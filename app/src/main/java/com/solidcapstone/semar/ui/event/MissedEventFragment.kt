package com.solidcapstone.semar.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.solidcapstone.semar.adapter.EventListAdapter
import com.solidcapstone.semar.databinding.FragmentMissedEventBinding
import java.util.Calendar
import java.util.Date


class MissedEventFragment : Fragment() {
    private lateinit var binding: FragmentMissedEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMissedEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showDummyListEvent()
        binding.progressBar.visibility = View.GONE
    }

    private fun showDummyListEvent() {
        val dummyEventList: ArrayList<Date> = arrayListOf()
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -3)
        for (i in 1..10) {
            cal.add(Calendar.HOUR, 17)
            dummyEventList.add(cal.time)
        }
        cal.time = Calendar.getInstance().time

        val eventListAdapter = EventListAdapter(
            dummyEventList.filter {
                it.before(cal.time)
            }
        )
        binding.rvEvent.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventListAdapter
        }
    }
}