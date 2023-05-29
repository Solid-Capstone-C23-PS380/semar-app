package com.solidcapstone.semar.ui.detail.video

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.solidcapstone.semar.data.Result
import com.solidcapstone.semar.databinding.ActivityVideoDetailBinding
import com.solidcapstone.semar.ui.detail.DetailViewModel
import com.solidcapstone.semar.utils.WayangViewModelFactory


class VideoDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityVideoDetailBinding

    private val viewModel: DetailViewModel by viewModels {
        WayangViewModelFactory.getInstance(this)
    }

    private var videos_id : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showWayangDetail()
        lifecycle.addObserver(binding.youtubePlayerView)

//        frameControl()
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = videos_id!!
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showWayangDetail() {
        val videoId = intent.getIntExtra(VideoDetailActivity.VIDEO_ID, 1)
        viewModel.getVideo(videoId).observe(this) { result ->
            when (result) {
                is Result.Loading -> binding.pbVideoDetail.visibility = View.VISIBLE

                is Result.Success -> {
                    val videoData = result.data
                    videos_id = videoData.videoId
                    binding.tvNameVideo.text = videoData.name
                    binding.pbVideoDetail.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.pbVideoDetail.visibility = View.GONE
                }
            }
        }
    }
//    private fun frameControl(){
//        val iFramePlayerOptions = IFramePlayerOptions.Builder()
//            .controls(1)
//            .fullscreen(1)
//            .build()
//
//       binding.youtubePlayerView.addFullscreenListener(object : FullscreenListener {
//            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: Function0<Unit>) {}
//            override fun onExitFullscreen() {}
//        })
//
//        binding.youtubePlayerView.initialize(object : YouTubePlayerListener {
//            override fun onApiChange(youTubePlayer: YouTubePlayer) {}
//            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {}
//            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {}
//
//            override fun onPlaybackQualityChange(
//                youTubePlayer: YouTubePlayer,
//                playbackQuality: PlayerConstants.PlaybackQuality
//            ) {}
//            override fun onPlaybackRateChange(
//                youTubePlayer: YouTubePlayer,
//                playbackRate: PlayerConstants.PlaybackRate
//            ) {}
//
//            override fun onReady(youTubePlayer: YouTubePlayer) {}
//            override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {}
//            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {}
//            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {}
//            override fun onVideoLoadedFraction(
//                youTubePlayer: YouTubePlayer,
//                loadedFraction: Float
//            ) {}
//        }, iFramePlayerOptions)
//    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release()
    }
    companion object {
        const val VIDEO_ID = "video_id"
    }
}