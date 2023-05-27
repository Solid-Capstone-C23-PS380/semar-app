package com.solidcapstone.semar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solidcapstone.semar.databinding.ItemImageBinding

class WayangDetailImageAdapter(
    private val listImage: List<String>
) : RecyclerView.Adapter<WayangDetailImageAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            Glide.with(itemView.context)
                .load(listImage[position])
                .into(binding.image)
        }
    }

    override fun getItemCount(): Int = listImage.size

}