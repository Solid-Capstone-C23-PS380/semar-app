package com.solidcapstone.semar.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.solidcapstone.semar.R
import com.solidcapstone.semar.adapter.EventPagerAdapter
import com.solidcapstone.semar.databinding.FragmentEventBinding


class EventFragment : Fragment() {
    private lateinit var binding: FragmentEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
    }

    private fun setViewPager() {
        val pagerAdapter = EventPagerAdapter(requireActivity() as AppCompatActivity)

        val viewPager: ViewPager2 = binding.viewpager
        viewPager.post { viewPager.setCurrentItem(1, false) }
        viewPager.adapter = pagerAdapter

        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.event_tab_missed,
            R.string.event_tab_active
        )
    }
}