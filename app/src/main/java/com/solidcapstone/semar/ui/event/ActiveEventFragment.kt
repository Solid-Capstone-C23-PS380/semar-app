package com.solidcapstone.semar.ui.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solidcapstone.semar.R
import com.solidcapstone.semar.databinding.FragmentActiveEventBinding
import java.util.Date


class ActiveEventFragment() : Fragment() {

    private var _binding: FragmentActiveEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActiveEventBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
}