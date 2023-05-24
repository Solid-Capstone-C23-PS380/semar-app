package com.solidcapstone.semar.ui.scan

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.solidcapstone.semar.databinding.FragmentResultTempBinding
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
        launcherIntentCameraX
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private const val ARG_IMAGE_URI = "arg_image_uri"

    }
    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            getFile = myFile
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.imageView.setImageBitmap(result)
        }
    }
}