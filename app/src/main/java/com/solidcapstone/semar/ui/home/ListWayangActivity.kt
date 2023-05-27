package com.solidcapstone.semar.ui.home

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.solidcapstone.semar.adapter.HomeWayangListAdapter
import com.solidcapstone.semar.data.Result
import com.solidcapstone.semar.databinding.ActivityListWayangBinding
import com.solidcapstone.semar.utils.WayangViewModelFactory

class ListWayangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListWayangBinding

    private val viewModel: HomeViewModel by viewModels {
        WayangViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListWayangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showListWayang()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showListWayang() {
        binding.rvWayang.layoutManager = GridLayoutManager(
            this,
            3
        )

        viewModel.getListWayang().observe(this) { result ->
            when (result) {
                is Result.Loading -> binding.pbWayang.visibility = View.VISIBLE

                is Result.Success -> {
                    val wayangListAdapter = HomeWayangListAdapter(result.data)
                    binding.rvWayang.adapter = wayangListAdapter
                    binding.pbWayang.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.pbWayang.visibility = View.GONE
                    Log.d("ListWayangActivity", result.toString())
                }
            }
        }
    }
}