package com.solidcapstone.semar.ui.event

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.solidcapstone.semar.R
import com.solidcapstone.semar.adapter.EventPagerAdapter
import com.solidcapstone.semar.databinding.FragmentEventBinding
import com.solidcapstone.semar.databinding.FragmentMissedEventBinding
import java.util.Date


class EventFragment : Fragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

//    private fun setViewPager() {
//        val pagerAdapter = EventPagerAdapter(Activity, date)
//
//        binding.apply {
//            viewPager = viewpager
//            tabs = tablayout
//        }
//
//        viewPager.adapter = pagerAdapter
//        val titles = resources.getStringArray(R.array.tab_menu)
//        TabLayoutMediator(tabs, viewPager) { tab, position ->
//            tab.text = titles[position]
//        }.attach()
//    }


}