package com.solidcapstone.semar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.solidcapstone.semar.databinding.ItemEventBinding

class EventAdapter( private val listEvent: List<String>
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listEvent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                listEvent[position],
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}