package com.solidcapstone.semar.ui.home

import androidx.lifecycle.ViewModel
import com.solidcapstone.semar.data.remote.repository.WayangRepository

class HomeViewModel(private val repository: WayangRepository) : ViewModel() {
    fun getListWayang(showLimit: Int? = null) = repository.getListWayang(showLimit)
}