package com.solidcapstone.semar.ui.detail

import androidx.lifecycle.ViewModel
import com.solidcapstone.semar.data.remote.repository.WayangRepository

class DetailViewModel(private val repository: WayangRepository) : ViewModel() {
    fun getWayang(id: Int) = repository.getWayang(id)
}