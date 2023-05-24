package com.solidcapstone.semar.ui.scan

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.solidcapstone.semar.databinding.FragmentResultTempBinding
import com.solidcapstone.semar.helper.rotateBitmap
import java.io.File
import com.solidcapstone.semar.R


class ResultTempFragment : Fragment() {

    private var _binding: FragmentResultTempBinding? = null
    private val binding get() = _binding!!
    private var getFile : File? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultTempBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnRemove.setOnClickListener {
            goBackToCameraFragment()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPhoto()
        onBackPressed()
//        getPhotoByGallery()
    }

    private fun getPhoto(){
        val picturePath = arguments?.getString("picture")
        val myFile = File(picturePath)
        val isBackCamera = arguments?.getBoolean("isBackCamera") ?: true
        getFile = myFile
        val result = BitmapFactory.decodeFile(myFile.path)
        rotateBitmap(result,isBackCamera)
        binding.imageView.setImageBitmap(result)

    }

//    private fun getPhotoByGallery(){
//        val file: File? = arguments?.getSerializable("file") as? File
//        getFile = file
//        val result = BitmapFactory.decodeFile(file?.path)
//        binding.imageView.setImageBitmap(result)
//    }

    private fun goBackToCameraFragment() {
        parentFragmentManager.popBackStack("ScanFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val fragment = parentFragmentManager.findFragmentById(R.id.fragmet_scan)

            if (fragment is ResultTempFragment) {
                parentFragmentManager.popBackStack(
                    "ScanFragment",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }
    companion object {
        const val CAMERA_X_RESULT = 200

    }
}