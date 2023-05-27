package com.solidcapstone.semar.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.solidcapstone.semar.adapter.HomeVideoListAdapter
import com.solidcapstone.semar.adapter.HomeWayangListAdapter
import com.solidcapstone.semar.data.Result
import com.solidcapstone.semar.databinding.FragmentHomeBinding
import com.solidcapstone.semar.ui.profile.ProfileActivity
import com.solidcapstone.semar.ui.video.ListVideoActivity
import com.solidcapstone.semar.utils.WayangViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels {
        WayangViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showListWayang()
        showDummyListVideo()

        binding.btnUserImage.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.btnMoreWayang.setOnClickListener {
            val intent = Intent(requireContext(), ListWayangActivity::class.java)
            startActivity(intent)
        }
        binding.btnMoreVideos.setOnClickListener {
            val intent = Intent(requireContext(), ListVideoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showListWayang() {
        binding.rvWayang.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        viewModel.getListWayang(2).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> binding.pbWayang.visibility = View.VISIBLE

                is Result.Success -> {
                    val wayangListAdapter = HomeWayangListAdapter(result.data)
                    binding.rvWayang.adapter = wayangListAdapter
                    binding.pbWayang.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.pbWayang.visibility = View.GONE
                    Log.d("HomeFragmentWayang", result.toString())
                }
            }
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