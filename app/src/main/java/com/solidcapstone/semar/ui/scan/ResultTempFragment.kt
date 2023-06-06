package com.solidcapstone.semar.ui.scan

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.solidcapstone.semar.R
import com.solidcapstone.semar.data.Result
import com.solidcapstone.semar.databinding.FragmentResultTempBinding
import com.solidcapstone.semar.helper.downscaleImage
import com.solidcapstone.semar.helper.reduceFileImg
import com.solidcapstone.semar.helper.rotateBitmap
import com.solidcapstone.semar.ui.detail.wayang.WayangDetailActivity
import com.solidcapstone.semar.utils.WayangViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ResultTempFragment : Fragment() {
    private lateinit var binding: FragmentResultTempBinding
    private var getFile: File? = null

    private val viewModel: ScanViewModel by viewModels {
        WayangViewModelFactory.getInstance(requireContext())
    }


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

        binding.btnScanCancel.setOnClickListener {
            goBackToScanFragment()
        }
        binding.btnScanOk.setOnClickListener {
            uploadImage()
        }
        binding.btnWayangDetail.setOnClickListener {

        }
    }

    private fun getPhoto() {
        val picturePath = arguments?.getString("picture") as String
        val myFile = File(picturePath)
        val isBackCamera = arguments?.getBoolean("isBackCamera") ?: true
        getFile = myFile
        val result = BitmapFactory.decodeFile(myFile.path)
        rotateBitmap(result, isBackCamera)
        binding.ivScannedPhoto.setImageBitmap(result)
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
        binding.ivScannedPhoto.setImageBitmap(result)
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

    private fun uploadImage() {
        if (getFile != null) {
            val file = downscaleImage(getFile as File)
            val fileReduce = reduceFileImg(file as File)

            val requestImageFile = fileReduce.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                fileReduce.name,
                requestImageFile
            )
            viewModel.predictWayang(imageMultipart).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.cardPrediction.visibility = View.GONE
                        binding.loadingScan.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        val wayangName = result.data.result
                        binding.tvPredictionResult.text = wayangName
                        binding.cardPrediction.visibility = View.VISIBLE
                        binding.loadingScan.visibility = View.GONE

                        binding.btnWayangDetail.setOnClickListener {
                            val intent = Intent(requireActivity(), WayangDetailActivity::class.java)
                            intent.putExtra(WayangDetailActivity.WAYANG_ID, result.data.id)
                            startActivity(intent)
                        }
                    }

                    is Result.Error -> {
                        showToast(getString(R.string.scan_error_prediction))
                        binding.loadingScan.visibility = View.GONE
                    }
                }
            }
        } else {
            showToast(resources.getString(R.string.error_choose_photo))
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_LONG
        ).show()
    }
}
