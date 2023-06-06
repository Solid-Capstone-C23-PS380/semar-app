package com.solidcapstone.semar.ui.profile.edit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.solidcapstone.semar.MainActivity
import com.solidcapstone.semar.R
import com.solidcapstone.semar.data.Result
import com.solidcapstone.semar.databinding.ActivityEditProfileBinding
import com.solidcapstone.semar.helper.reduceFileImg
import com.solidcapstone.semar.helper.uriToFile
import com.solidcapstone.semar.ui.profile.ProfileActivity
import com.solidcapstone.semar.utils.WayangViewModelFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var signInMethod: String

    private val viewModel: EditProfileViewModel by viewModels {
        WayangViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        signInMethod = currentUser?.providerData?.last()?.providerId.toString()

        binding.tvName.setText(currentUser?.displayName)
        binding.tvEmail.setText(currentUser?.email)
        Glide.with(this)
            .load(currentUser?.photoUrl)
            .placeholder(R.drawable.ic_person)
            .signature(ObjectKey(System.currentTimeMillis().toString()))
            .into(binding.ivUserImage)

        if (signInMethod == GoogleAuthProvider.PROVIDER_ID) {
            binding.apply {
                tvEmail.isEnabled = false
                tvEmail.alpha = 0.25f
                tvPassword.isEnabled = false
                tvPassword.alpha = 0.25f
            }
        }

        binding.tvEmail.addTextChangedListener { input ->
            binding.tilPassword.visibility =
                if (input.toString() == currentUser?.email.toString()) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
        }

        binding.btnChangeUserImage.setOnClickListener { startIntentGallery() }
        binding.btnSave.setOnClickListener { saveProfile() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveProfile() {
        val currentUser = auth.currentUser

        if (binding.tvPassword.text.isNullOrEmpty()
            && signInMethod == EmailAuthProvider.PROVIDER_ID
            && binding.tvEmail.text.toString() != currentUser?.email
        ) {
            showToast(getString(R.string.profile_edit_error_fill_password))
            return
        }

        setLoadingVisibility(true)

        if (signInMethod == EmailAuthProvider.PROVIDER_ID
            && binding.tvEmail.text.toString() != currentUser?.email
        ) {
            val credential = EmailAuthProvider.getCredential(
                currentUser?.email.toString(),
                binding.tvPassword.text.toString(),
            )
            currentUser?.reauthenticate(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    currentUser.updateEmail(binding.tvEmail.text.toString())
                } else {
                    setLoadingVisibility(false)
                    showToast(getString(R.string.error_happened))

                    Log.d(TAG, task.result.toString())
                }
            }
        }

        val profileUpdates = userProfileChangeRequest {
            displayName = binding.tvName.text.toString()
        }
        currentUser?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    refreshProfileData()
                } else {
                    setLoadingVisibility(false)
                    showToast(getString(R.string.error_happened))
                }
            }
    }

    private fun refreshProfileData() {
        startActivity(Intent(this, MainActivity::class.java))
        startActivity(Intent(this, ProfileActivity::class.java))
    }

    private fun startIntentGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.input_choose_from_gallery))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val currentUser = auth.currentUser

            val selectedImg = result.data?.data as Uri
            var file = uriToFile(selectedImg, this)
            file = reduceFileImg(file, 500000)
            val requestFileImage = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestFileImage
            )

            viewModel.uploadProfilePicture(
                currentUser?.uid.toString(),
                imageMultiPart
            ).observe(this) { uploadResult ->
                when (uploadResult) {
                    is Result.Loading -> setLoadingVisibility(true)
                    is Result.Success -> {
                        val profileUpdates = userProfileChangeRequest {
                            photoUri = Uri.parse(uploadResult.data.imageUrl)
                        }
                        currentUser?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    refreshProfileData()
                                    startActivity(
                                        Intent(
                                            this,
                                            EditProfileActivity::class.java
                                        )
                                    )
                                } else {
                                    setLoadingVisibility(false)
                                    showToast(getString(R.string.error_happened))
                                    Log.w(TAG, task.result.toString())
                                }
                            }
                    }

                    is Result.Error -> {
                        setLoadingVisibility(false)
                        showToast(getString(R.string.error_happened))
                        Log.w(TAG, uploadResult.error)
                    }
                }
            }
        }
    }

    private fun setLoadingVisibility(isVisible: Boolean) {
        binding.apply {
            pbEditProfile.visibility = if (isVisible) View.VISIBLE else View.GONE
            overlayPbEditProfile.visibility = if (isVisible) View.VISIBLE else View.GONE
            tvName.isEnabled = !isVisible
            tvEmail.isEnabled = !isVisible
            tvPassword.isEnabled = !isVisible
            btnSave.isEnabled = !isVisible
            btnChangeUserImage.isEnabled = !isVisible
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "EditProfileActivity"
    }
}