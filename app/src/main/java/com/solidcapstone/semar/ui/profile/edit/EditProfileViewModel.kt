package com.solidcapstone.semar.ui.profile.edit

import androidx.lifecycle.ViewModel
import com.solidcapstone.semar.data.remote.repository.WayangRepository
import okhttp3.MultipartBody

class EditProfileViewModel(private val repository: WayangRepository) : ViewModel() {
    fun uploadProfilePicture(
        uid: String,
        file: MultipartBody.Part,
    ) = repository.uploadProfilePhoto(uid, file)
}