package com.solidcapstone.semar.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solidcapstone.semar.data.local.entity.EventEntity
import com.solidcapstone.semar.databinding.ItemEventBinding
import com.solidcapstone.semar.ui.detail.event.EventDetailActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class EventListAdapter(
    private val listEventItem: List<EventEntity>,
) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listEventItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listEventItem[position]

        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
        dateFormat.timeZone = TimeZone.getTimeZone(TIME_ZONE)
        val date = dateFormat.parse(currentItem.time) as Date
        dateFormat.timeZone = TimeZone.getDefault()
        val formattedDate = DateFormat.getDateTimeInstance().format(date)

        holder.apply {
            binding.tvNameEvent.text = currentItem.name
            Glide.with(itemView.context)
                .load(currentItem.photoUrl)
                .into(binding.ivEvent)
            binding.tvDateEvent.text = formattedDate

            itemView.setOnClickListener {
                val intent = Intent(it.context, EventDetailActivity::class.java)
                intent.putExtra(EventDetailActivity.EVENT_ID, currentItem.id)
                it.context.startActivity(intent)
            }
        }

    }

    companion object {
        private const val DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z"
        private const val TIME_ZONE = "UTC"
    }
}