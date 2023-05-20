package com.solidcapstone.semar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.solidcapstone.semar.adapter.HomeVideoListAdapter
import com.solidcapstone.semar.adapter.HomeWayangListAdapter
import com.solidcapstone.semar.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showDummyListWayang()
        showDummyListVideo()
    }

    private fun showDummyListWayang() {
        val dummyListWayang = HomeWayangListAdapter(
            listOf("A", "B", "C", "D", "E")
        )

        binding.rvWayang.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = dummyListWayang
        }
    }

    private fun showDummyListVideo() {
        val dummyListVideo = HomeVideoListAdapter(
            listOf("A", "B", "C", "D", "E")
        )

        binding.rvVideos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dummyListVideo
        }
    }

}