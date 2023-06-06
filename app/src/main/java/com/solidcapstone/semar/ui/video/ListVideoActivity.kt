package com.solidcapstone.semar.ui.video

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.solidcapstone.semar.adapter.VideoListAdapter
import com.solidcapstone.semar.data.Result
import com.solidcapstone.semar.databinding.ActivityListVideoBinding
import com.solidcapstone.semar.ui.home.HomeViewModel
import com.solidcapstone.semar.utils.WayangViewModelFactory

class ListVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListVideoBinding

    private val viewModel: HomeViewModel by viewModels {
        WayangViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showListVideo()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showListVideo() {
        binding.rvVideo.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        viewModel.getListVideo().observe(this) { result ->
            when (result) {
                is Result.Loading -> binding.pbVideo.visibility = View.VISIBLE

                is Result.Success -> {
                    val videoListAdapter = VideoListAdapter(result.data)
                    binding.rvVideo.adapter = videoListAdapter
                    binding.pbVideo.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.pbVideo.visibility = View.GONE
                    Log.d(TAG, result.toString())
                }
            }
        }
    }

    companion object {
        private const val TAG = "HomeFragmentWayang"
    }
}