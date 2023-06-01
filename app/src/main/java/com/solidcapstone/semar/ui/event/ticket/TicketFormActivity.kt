package com.solidcapstone.semar.ui.event.ticket

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.solidcapstone.semar.R
import com.solidcapstone.semar.databinding.ActivityTicketFormBinding
import com.solidcapstone.semar.helper.withCurrencyFormat

class TicketFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTicketFormBinding
    private val viewModel: TicketViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val paymentMethods = listOf(
            "Dana - 089507741841 (a.n Bahrum Nisar)",
            "GoPay - 0895606819311 (a.n. Bahrum Nisar)",
            "BNI - 3012830193 (a.n. Bahrum Nisar)",
            "BRI - 187439402423042 (a.n. Bahrum Nisar)",
            "Jago - 173210392 (a.n. Bahrum Nisar)")

        val paymentMethodAdapter = ArrayAdapter(this, R.layout.item_dropdown, paymentMethods)
        binding.inputPaymentMethod.setAdapter(paymentMethodAdapter)

        val price = intent.getIntExtra(EVENT_PRICE,0)

        viewModel.totalPrice.observe(this) { totalPrice ->
            binding.tvTotalCost.text = totalPrice.toString().withCurrencyFormat()
        }


        binding.inputTicketAmount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString().toIntOrNull() ?: 0
                val totalPrice = viewModel.calculateTotalPrice(price, input)
                viewModel.onTotalPriceChanged(totalPrice)
                binding.tvTotalCost.text = totalPrice.toString().withCurrencyFormat()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val EVENT_ID = "event_id"
        const val EVENT_PRICE = "event_price"
    }
}