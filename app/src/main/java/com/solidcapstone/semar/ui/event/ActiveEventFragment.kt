package com.solidcapstone.semar.ui.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.solidcapstone.semar.adapter.EventListAdapter
import com.solidcapstone.semar.data.Result
import com.solidcapstone.semar.databinding.FragmentActiveEventBinding
import com.solidcapstone.semar.utils.WayangViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActiveEventFragment : Fragment() {
    private lateinit var binding: FragmentActiveEventBinding

    private val viewModel: EventViewModel by viewModels {
        WayangViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActiveEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showListEvent()
    }

//    private fun showDummyListEvent() {
//        val dummyEventList: ArrayList<Date> = arrayListOf()
//        val cal = Calendar.getInstance()
//        cal.add(Calendar.DAY_OF_YEAR, -3)
//        for (i in 1..10) {
//            cal.add(Calendar.HOUR, 17)
//            dummyEventList.add(cal.time)
//        }
//        cal.time = Calendar.getInstance().time
//
//        val eventListAdapter = EventListAdapter(
//            dummyEventList.filter {
//                it.after(cal.time)
//            }
//        )
//        binding.rvEvent.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = eventListAdapter
//        }
//    }
    private fun showListEvent() {
        val currentTime = Date()
        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)

        binding.rvEvent.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        viewModel.getListEvent().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> binding.pbEvent.visibility = View.VISIBLE

                is Result.Success -> {
                    val filteredEvents = result.data.filter {
                        val eventTime = dateFormat.parse(it.time)
                        eventTime !=null && eventTime.after(currentTime)
                    }
                    val eventListAdapter = EventListAdapter(filteredEvents)
                    binding.rvEvent.adapter = eventListAdapter
                    binding.pbEvent.visibility = View.GONE
                }

                is Result.Error -> {
                    binding.pbEvent.visibility = View.GONE
                    Log.d("ListEventFragment", result.toString())
                }
            }
        }
    }
}