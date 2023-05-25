package com.solidcapstone.semar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solidcapstone.semar.databinding.ItemEventBinding
import java.text.DateFormat
import java.util.Date

class EventListAdapter(
    private val listEvent: List<Date>
) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listEvent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listEvent[position]
        val dateFormatted = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
        holder.binding.tvDateEvent.text = dateFormatted.format(currentItem.time)
    }
}