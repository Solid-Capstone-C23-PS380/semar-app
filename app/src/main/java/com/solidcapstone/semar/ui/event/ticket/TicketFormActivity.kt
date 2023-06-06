package com.solidcapstone.semar.ui.event.ticket

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.solidcapstone.semar.R
import com.solidcapstone.semar.data.Result
import com.solidcapstone.semar.databinding.ActivityTicketFormBinding
import com.solidcapstone.semar.helper.downscaleImage
import com.solidcapstone.semar.helper.reduceFileImg
import com.solidcapstone.semar.helper.uriToFile
import com.solidcapstone.semar.helper.withCurrencyFormat
import com.solidcapstone.semar.utils.WayangViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*

class TicketFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTicketFormBinding
    private val viewModel: TicketViewModel by viewModels {
        WayangViewModelFactory.getInstance(this@TicketFormActivity)
    }
    private var getFile: File? = null
    private var getPaymentMethod: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_buy_ticket)

        val paymentMethods = resources.getStringArray(R.array.ticket_payment_methods)

        val paymentMethodAdapter = ArrayAdapter(this, R.layout.item_dropdown, paymentMethods)
        binding.inputPaymentMethod.setAdapter(paymentMethodAdapter)

        binding.inputPaymentMethod.setOnItemClickListener { parent, _, position, _ ->
            val selectedPaymentMethod = parent.getItemAtPosition(position).toString()
            getPaymentMethod = selectedPaymentMethod
        }

        val price = intent.getIntExtra(EVENT_PRICE, 0)

        viewModel.totalPrice.observe(this) { totalPrice ->
            binding.tvTotalCost.text = totalPrice.toString().withCurrencyFormat()
        }


        binding.inputTicketAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString().toIntOrNull() ?: 0
                val totalPrice = viewModel.calculateTotalPrice(price, input)
                viewModel.onTotalPriceChanged(totalPrice)
                binding.tvTotalCost.text = totalPrice.toString().withCurrencyFormat()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.inputBtnProofOfPayment.setOnClickListener { startGallery() }

        binding.btnBuyTicket.setOnClickListener {
            buyTicket()
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.input_choose_from_gallery))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this@TicketFormActivity)

            getFile = myFile

            binding.ivProofOfPayment.setImageURI(selectedImg)
            binding.ivProofOfPayment.isVisible = true
            binding.tvProofOfPaymentName.text = myFile.name.toString()
        }
    }

    private fun buyTicket() {
        if (binding.inputName.text.isNullOrEmpty()) {
            showToast(getString(R.string.error_input_name_empty))
            return
        }
        if (binding.inputEmail.text.isNullOrEmpty()) {
            showToast(getString(R.string.error_input_email_empty))
            return
        }
        if (binding.inputTicketAmount.text.isNullOrEmpty()) {
            showToast(getString(R.string.ticket_error_ticket_amount_empty))
            return
        }
        if (binding.inputPaymentMethod.text.isNullOrEmpty()) {
            showToast(getString(R.string.ticket_error_payment_method_empty))
            return
        }
        if (getFile == null) {
            showToast(resources.getString(R.string.error_choose_photo))
            return
        }

        val file = downscaleImage(getFile as File)
        val fileReduce = reduceFileImg(file as File)

        val requestImageFile = fileReduce.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            fileReduce.name,
            requestImageFile
        )
        val eventId = intent.getIntExtra(EVENT_ID, 0)
        binding.apply {
            val ticketBought = inputTicketAmount.text.toString().toInt()
            val name = inputName.text.toString()
            val email = inputEmail.text.toString()
            val paymentMethod = getPaymentMethod!!
            viewModel.buyTicket(
                eventId,
                ticketBought,
                name,
                email,
                paymentMethod,
                imageMultipart
            ).observe(this@TicketFormActivity) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoadingVisibility(true)
                    }

                    is Result.Success -> {
                        val ticketStatus = result.data.message
                        if (ticketStatus == "success") {
                            val intent =
                                Intent(this@TicketFormActivity, BuySuccessActivity::class.java)
                            intent.putExtra(
                                BuySuccessActivity.EVENT_ID,
                                result.data.data.eventId
                            )
                            startActivity(intent)
                        }
                        showToast(getString(R.string.ticket_buy_success))
                    }

                    is Result.Error -> {
                        showLoadingVisibility(false)
                        showToast(getString(R.string.ticket_error_buy_failed))
                    }
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoadingVisibility(isVisible: Boolean) {
        binding.apply {
            pbTicketForm.visibility = if (isVisible) View.VISIBLE else View.GONE
            overlayPbTicketForm.visibility = if (isVisible) View.VISIBLE else View.GONE
            inputName.isEnabled = !isVisible
            inputEmail.isEnabled = !isVisible
            inputTicketAmount.isEnabled = !isVisible
            inputPaymentMethod.isEnabled = !isVisible
            inputBtnProofOfPayment.isEnabled = !isVisible
            btnBuyTicket.isEnabled = !isVisible
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(
            this,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        const val EVENT_ID = "event_id"
        const val EVENT_PRICE = "event_price"
    }
}