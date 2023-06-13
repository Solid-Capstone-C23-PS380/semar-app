package com.solidcapstone.semar.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solidcapstone.semar.data.local.entity.VideoEntity
import com.solidcapstone.semar.databinding.ItemVideoBinding
import com.solidcapstone.semar.ui.detail.video.VideoDetailActivity

class VideoListAdapter(
    private val listVideoItem: List<VideoEntity>,
) : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listVideoItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listVideoItem[position]

        holder.apply {
            binding.tvNameVideo.text = currentItem.name
            binding.tvDurationVideo.text = currentItem.videoDuration
            Glide.with(itemView.context)
                .load(currentItem.photoUrl)
                .into(binding.ivVideoImage)

            itemView.setOnClickListener {
                val intent = Intent(it.context, VideoDetailActivity::class.java)
                intent.putExtra(VideoDetailActivity.VIDEO_ID, currentItem.id)
                it.context.startActivity(intent)
            }
        }
    }
}