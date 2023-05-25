package com.solidcapstone.semar.ui.event

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.solidcapstone.semar.R
import com.solidcapstone.semar.adapter.EventPagerAdapter
import com.solidcapstone.semar.databinding.FragmentEventBinding
import com.solidcapstone.semar.databinding.FragmentMissedEventBinding
import java.util.Date


class EventFragment : Fragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager : ViewPager2
    private lateinit var tabs : TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewPager()
    }

    private fun setViewPager(){
        val pagerAdapter = EventPagerAdapter(requireActivity())
        binding.apply {
            viewPager = viewpager
            tabs = tablayout
        }
        viewPager.adapter = pagerAdapter
        val title = resources.getStringArray(R.array.tab_menu)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = title[position]
        }.attach()
    }

}