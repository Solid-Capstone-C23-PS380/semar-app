package com.solidcapstone.semar.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.solidcapstone.semar.adapter.HomeVideoListAdapter
import com.solidcapstone.semar.adapter.HomeWayangListAdapter
import com.solidcapstone.semar.databinding.FragmentHomeBinding
import com.solidcapstone.semar.ui.auth.AuthActivity
import com.solidcapstone.semar.ui.profile.ProfileActivity

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

        binding.btnUserImage.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        val intent = Intent(requireContext(), AuthActivity::class.java)
        startActivity(intent)
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