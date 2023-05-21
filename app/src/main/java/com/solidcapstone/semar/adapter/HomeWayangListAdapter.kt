package com.solidcapstone.semar.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solidcapstone.semar.databinding.ItemWayangBinding
import com.solidcapstone.semar.ui.detail.wayang.WayangDetailActivity

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
        holder.itemView.apply {
            setOnClickListener {
                val intent = Intent(context, WayangDetailActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}