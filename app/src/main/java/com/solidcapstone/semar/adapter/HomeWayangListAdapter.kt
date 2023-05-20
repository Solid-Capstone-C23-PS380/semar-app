package com.solidcapstone.semar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.solidcapstone.semar.databinding.ItemWayangBinding

class HomeWayangListAdapter(
    private val listWayang: List<String>
) : RecyclerView.Adapter<HomeWayangListAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemWayangBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWayangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listWayang.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                listWayang[position],
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}