package com.solidcapstone.semar.ui.event.ticket

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.solidcapstone.semar.R
import com.solidcapstone.semar.databinding.ActivityTicketFormBinding

class TicketFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTicketFormBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val paymentMethods = listOf("Tunai", "GoPay")
        val paymentMethodAdapter = ArrayAdapter(this, R.layout.item_dropdown, paymentMethods)
        binding.inputPaymentMethod.setAdapter(paymentMethodAdapter)

        binding.inputName.setText("Lorem Ipsumin")
        binding.inputEmail.setText("loremipsumin@gmail.com")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}