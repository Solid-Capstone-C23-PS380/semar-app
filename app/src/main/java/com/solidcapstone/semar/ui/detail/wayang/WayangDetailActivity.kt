package com.solidcapstone.semar.ui.detail.wayang

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.solidcapstone.semar.adapter.WayangDetailImageAdapter
import com.solidcapstone.semar.data.Result
import com.solidcapstone.semar.databinding.ActivityWayangDetailBinding
import com.solidcapstone.semar.ui.detail.DetailViewModel
import com.solidcapstone.semar.utils.WayangViewModelFactory

class WayangDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWayangDetailBinding

    private val viewModel: DetailViewModel by viewModels {
        WayangViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWayangDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showWayangDetail()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showWayangDetail() {
        val wayangId = intent.getIntExtra(WAYANG_ID, 1)
        viewModel.getWayang(wayangId).observe(this) { result ->
            when (result) {
                is Result.Loading -> binding.pbWayangDetail.visibility = View.VISIBLE

                is Result.Success -> {
                    val wayangData = result.data
                    val viewPagerAdapter = WayangDetailImageAdapter(wayangData.photoUrl)
                    binding.vpWayangImages.adapter = viewPagerAdapter
                    binding.indicatorWayangImages.setViewPager(binding.vpWayangImages)

                    supportActionBar?.title = wayangData.name
                    binding.tvWayangDescription.text = wayangData.description
                    binding.pbWayangDetail.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.pbWayangDetail.visibility = View.GONE
                    Log.d("HomeFragmentWayang", result.toString())
                }
            }
        }
    }

    companion object {
        const val WAYANG_ID = "wayang_id"
    }
}