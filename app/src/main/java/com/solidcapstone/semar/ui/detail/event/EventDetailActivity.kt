package com.solidcapstone.semar.ui.detail.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.solidcapstone.semar.R
import com.solidcapstone.semar.data.Result
import com.solidcapstone.semar.databinding.ActivityEventDetailBinding
import com.solidcapstone.semar.ui.detail.DetailViewModel
import com.solidcapstone.semar.utils.WayangViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEventDetailBinding

    private val viewModel: DetailViewModel by viewModels {
        WayangViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showEventDetail()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showEventDetail() {
        val eventId = intent.getIntExtra(EVENT_ID,1)
        viewModel.getEvent(eventId).observe(this) { result ->
            when (result) {
                is Result.Loading -> binding.pbEventDetail.visibility = View.VISIBLE

                is Result.Success -> {
                    val eventData = result.data
                    val currentTime = Date()
                    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
                    val dateEvent = dateFormat.parse(eventData.time)

                    Glide.with(this)
                        .load(eventData.photoUrl)
                        .into(binding.ivEvent)
                    binding.tvPrice.text ="Rp ${eventData.price}"

                    supportActionBar?.title = eventData.name
                    binding.tvName.text = eventData.name
                    binding.tvEventDescription.text = eventData.description

                    if (dateEvent != null) {
                        if(dateEvent.after(currentTime)){
                            binding.btnBuyTicket.isEnabled = true
                        }else{
                            binding.btnBuyTicket.isEnabled = false
                            binding.btnBuyTicket.setBackgroundColor(resources.getColor(R.color.brown_100))
                        }
                    }
                    binding.pbEventDetail.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.pbEventDetail.visibility = View.GONE
                    Log.d("EventDetailActivity", result.toString())
                }
            }
        }
    }

    companion object {
        const val EVENT_ID = "event_id"
    }
}