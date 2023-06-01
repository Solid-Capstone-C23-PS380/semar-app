package com.solidcapstone.semar.ui.event.ticket

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.solidcapstone.semar.databinding.ActivityBuySuccessBinding
import com.solidcapstone.semar.ui.detail.event.EventDetailActivity

class BuySuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuySuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuySuccessBinding.inflate(layoutInflater)

        val eventId = intent.getIntExtra(EVENT_ID, 1)
        binding.btnFinish.setOnClickListener {
            val intent = Intent(this@BuySuccessActivity, EventDetailActivity::class.java)
            intent.putExtra(EventDetailActivity.EVENT_ID, eventId)
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    companion object {
        const val EVENT_ID = "event_id"
    }
}