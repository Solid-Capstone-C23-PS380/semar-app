package com.solidcapstone.semar.ui.event

import androidx.lifecycle.ViewModel
import com.solidcapstone.semar.data.remote.repository.WayangRepository

class EventViewModel(private val repository: WayangRepository) : ViewModel() {
    fun getListEvent() = repository.getListEvent()
}