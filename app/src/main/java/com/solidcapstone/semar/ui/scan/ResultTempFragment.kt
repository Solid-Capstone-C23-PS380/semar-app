package com.solidcapstone.semar.ui.scan

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.solidcapstone.semar.R
import com.solidcapstone.semar.databinding.FragmentResultTempBinding
import com.solidcapstone.semar.helper.rotateBitmap
import java.io.File

class ResultTempFragment : Fragment() {
    private lateinit var binding: FragmentResultTempBinding
    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultTempBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val media = arguments?.getString("media")
        if (media == "camera") {
            getPhoto()
        } else if (media == "gallery") {
            getPhotoByGallery()
        }
        onBackPressed()

        binding.btnRemove.setOnClickListener {
            goBackToScanFragment()
        }
    }

    private fun getPhoto() {
        val picturePath = arguments?.getString("picture") as String
        val myFile = File(picturePath)
        val isBackCamera = arguments?.getBoolean("isBackCamera") ?: true
        getFile = myFile
        val result = BitmapFactory.decodeFile(myFile.path)
        rotateBitmap(result, isBackCamera)
        binding.imageView.setImageBitmap(result)
    }

    private fun getPhotoByGallery() {
        val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("file", File::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable("file")
        } as File

        getFile = myFile
        val result = BitmapFactory.decodeFile(myFile.path)
        binding.imageView.setImageBitmap(result)
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val fragment = parentFragmentManager.findFragmentById(R.id.fragment_scan)

            if (fragment is ResultTempFragment) {
                goBackToScanFragment()
            } else {
                isEnabled = false
                onBackPressed()
            }
        }
    }

    private fun goBackToScanFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_scan, ScanFragment())
            .commit()
    }
}