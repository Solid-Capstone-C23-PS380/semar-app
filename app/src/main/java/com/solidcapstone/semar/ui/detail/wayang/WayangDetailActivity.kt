package com.solidcapstone.semar.ui.detail.wayang

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.solidcapstone.semar.adapter.WayangDetailImageAdapter
import com.solidcapstone.semar.databinding.ActivityWayangDetailBinding

class WayangDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWayangDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWayangDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewPagerAdapter = WayangDetailImageAdapter(
            this@WayangDetailActivity,
            arrayListOf(0, 0, 0, 0)
        )
        binding.vpWayangImages.adapter = viewPagerAdapter
        binding.indicatorWayangImages.setViewPager(binding.vpWayangImages)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}