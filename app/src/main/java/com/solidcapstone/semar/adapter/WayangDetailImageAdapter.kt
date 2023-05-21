package com.solidcapstone.semar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.solidcapstone.semar.R
import com.solidcapstone.semar.databinding.ItemImageBinding

class WayangDetailImageAdapter(
    private val context: Context,
    private var imageList: ArrayList<Int>
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
        holder.binding.image.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_launcher_background
            )
        )
    }

    override fun getItemCount(): Int = imageList.size

}