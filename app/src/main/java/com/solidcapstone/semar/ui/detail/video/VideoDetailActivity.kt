package com.solidcapstone.semar.ui.detail.video

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.solidcapstone.semar.databinding.ActivityVideoDetailBinding

class VideoDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityVideoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    companion object {
        const val VIDEO_ID = "video_id"
    }
}