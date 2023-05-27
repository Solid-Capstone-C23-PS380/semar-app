package com.solidcapstone.semar.ui.scan

import androidx.lifecycle.ViewModel
import com.solidcapstone.semar.data.remote.repository.WayangRepository
import okhttp3.MultipartBody

class ScanViewModel(private val repository: WayangRepository) : ViewModel() {
    fun predictWayang(imgMultipart: MultipartBody.Part) = repository.predictWayang(imgMultipart)
}