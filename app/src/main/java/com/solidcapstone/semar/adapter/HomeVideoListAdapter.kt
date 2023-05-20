package com.solidcapstone.semar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.solidcapstone.semar.databinding.ItemVideoBinding

class HomeVideoListAdapter(
    private val listVideo: List<String>
) : RecyclerView.Adapter<HomeVideoListAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listVideo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                listVideo[position],
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}