package com.solidcapstone.semar.ui.detail.video

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showVideoDetail()
        initializeVideoPlayer()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showVideoDetail() {
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

    private fun initializeVideoPlayer() {
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
                    setSystemBarVisibility(false)
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                }

                @SuppressLint("SourceLockedOrientationActivity")
                override fun onExitFullscreen() {
                    youtubePlayerView.visibility = View.VISIBLE
                    fullScreenViewContainer.visibility = View.GONE
                    fullScreenViewContainer.removeAllViews()
                    setSystemBarVisibility(true)
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

    private fun setSystemBarVisibility(isVisible: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isVisible) {
                window.decorView.windowInsetsController?.show(WindowInsetsCompat.Type.systemBars())
            } else {
                window.decorView.windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())
                window.decorView.windowInsetsController?.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            if (isVisible) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            } else {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_FULLSCREEN
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