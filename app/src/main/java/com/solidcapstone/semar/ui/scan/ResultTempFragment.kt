package com.solidcapstone.semar.ui.scan

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.solidcapstone.semar.databinding.FragmentResultTempBinding
import com.solidcapstone.semar.helper.rotateBitmap
import java.io.File


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
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPhoto()
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
    companion object {
        const val CAMERA_X_RESULT = 200

    }
}