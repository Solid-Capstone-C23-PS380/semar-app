package com.solidcapstone.semar.ui.detail

import androidx.lifecycle.ViewModel
import com.solidcapstone.semar.data.remote.repository.WayangRepository

class DetailViewModel(private val repository: WayangRepository) : ViewModel() {
    fun getWayang(id: Int) = repository.getWayang(id)
    fun getVideo(id: Int) = repository.getVideo(id)
    fun getEvent(id: Int) = repository.getEvent(id)
}