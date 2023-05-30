package com.solidcapstone.semar.ui.detail.video

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.solidcapstone.semar.data.Result
import com.solidcapstone.semar.databinding.ActivityVideoDetailBinding
import com.solidcapstone.semar.ui.detail.DetailViewModel
import com.solidcapstone.semar.utils.WayangViewModelFactory


class VideoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoDetailBinding

    private val viewModel: DetailViewModel by viewModels {
        WayangViewModelFactory.getInstance(this)
    }

    private var videoIdYoutube: String? = null
    private var videoIsFullscreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showWayangDetail()
        lifecycle.addObserver(binding.youtubePlayerView)

        val iFramePlayerOptions: IFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(1)
            .build()

        binding.apply {
            youtubePlayerView.addFullscreenListener(object : FullscreenListener {
                override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                    youtubePlayerView.visibility = View.GONE
                    fullScreenViewContainer.visibility = View.VISIBLE
                    fullScreenViewContainer.addView(fullscreenView)
                    videoIsFullscreen = true
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                }

                @SuppressLint("SourceLockedOrientationActivity")
                override fun onExitFullscreen() {
                    youtubePlayerView.visibility = View.VISIBLE
                    fullScreenViewContainer.visibility = View.GONE
                    fullScreenViewContainer.removeAllViews()
                    videoIsFullscreen = false
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                }
            })

            youtubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoIdYoutube ?: "dQw4w9WgXcQ", 0f)
                }
            }, iFramePlayerOptions)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showWayangDetail() {
        val videoId = intent.getIntExtra(VIDEO_ID, 1)
        viewModel.getVideo(videoId).observe(this) { result ->
            when (result) {
                is Result.Loading -> binding.pbVideoDetail.visibility = View.VISIBLE

                is Result.Success -> {
                    val videoData = result.data
                    videoIdYoutube = videoData.videoId
                    supportActionBar?.title = videoData.name
                    binding.tvNameVideo.text = videoData.name
                    binding.pbVideoDetail.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.pbVideoDetail.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release()
    }

    companion object {
        const val VIDEO_ID = "video_id"
    }
}