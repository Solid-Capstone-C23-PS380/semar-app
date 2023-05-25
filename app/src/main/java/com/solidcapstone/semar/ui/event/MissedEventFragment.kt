package com.solidcapstone.semar.ui.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solidcapstone.semar.R
import com.solidcapstone.semar.databinding.FragmentMissedEventBinding
import java.util.Date


class MissedEventFragment() : Fragment() {

    private var _binding: FragmentMissedEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMissedEventBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

}