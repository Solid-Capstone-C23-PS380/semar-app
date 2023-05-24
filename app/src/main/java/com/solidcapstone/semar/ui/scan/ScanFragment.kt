package com.solidcapstone.semar.ui.scan

import android.Manifest.permission.CAMERA
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.solidcapstone.semar.databinding.FragmentScanBinding
import com.solidcapstone.semar.helper.createFile
import java.io.File
import com.solidcapstone.semar.R
import com.solidcapstone.semar.helper.uriToFile


class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private lateinit var cameraProvider: ProcessCameraProvider
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    requireContext(),
                    "Tidak mendapatkan izin.",
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnCaptureImage.setOnClickListener{
            takePhoto()
        }
        binding.btnGalery.setOnClickListener{
            startGallery()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            requestPermissions(
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        startCamera()
    }
    private fun startGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri? = result.data?.data
            selectedImg?.let { uri ->
                val file = uriToFile(uri, requireContext())

                val resultTempFragment = ResultTempFragment()
                val bundle = Bundle()
                bundle.apply {
                    putSerializable("file", file)
                    putString("media","gallery")
                }
                resultTempFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmet_scan, resultTempFragment)
                    .addToBackStack("ScanFragment")
                    .commit()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindPreview()
        }, ContextCompat.getMainExecutor(requireContext()))
    }
    private fun bindPreview() {
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
        }
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                viewLifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        } catch (exc: Exception) {
            Toast.makeText(requireContext(), "Failed to bind camera", Toast.LENGTH_SHORT).show()
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = createFile(requireActivity().application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    navigateToResultFragment(photoFile, cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
//                    val intent = Intent().apply {
//                        putExtra("picture", photoFile)
//                        putExtra("isBackCamera", cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
//                    }
//                    requireActivity().setResult(ResultTempFragment.CAMERA_X_RESULT, intent)
//                    requireActivity().finish()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(requireContext(), "Failed to capture photo", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun navigateToResultFragment(photoFile: File, isBackCamera: Boolean) {
        val bundle = Bundle().apply {
            putString("picture", photoFile.absolutePath)
            putBoolean("isBackCamera", isBackCamera)
            putString("media","camera")
        }

        val resultFragment = ResultTempFragment()
        resultFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmet_scan, resultFragment)
            .addToBackStack("ScanFragment")
            .commit()
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}