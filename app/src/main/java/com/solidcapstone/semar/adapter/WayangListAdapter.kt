package com.solidcapstone.semar.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solidcapstone.semar.data.local.entity.WayangEntity
import com.solidcapstone.semar.databinding.ItemWayangBinding
import com.solidcapstone.semar.ui.detail.wayang.WayangDetailActivity

class WayangListAdapter(
    private val listWayangItem: List<WayangEntity>
) : RecyclerView.Adapter<WayangListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemWayangBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWayangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listWayangItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listWayangItem[position]

        holder.apply {
            binding.tvWayangName.text = currentItem.name
            Glide.with(itemView.context)
                .load(currentItem.photoUrl[0])
                .into(binding.ivWayang)

            itemView.setOnClickListener {
                val intent = Intent(it.context, WayangDetailActivity::class.java)
                intent.putExtra(WayangDetailActivity.WAYANG_ID, currentItem.id)
                it.context.startActivity(intent)
            }
        }
    }
}