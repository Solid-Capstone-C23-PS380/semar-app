package com.solidcapstone.semar.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solidcapstone.semar.data.remote.repository.WayangRepository
import com.solidcapstone.semar.di.Injection
import com.solidcapstone.semar.ui.detail.DetailViewModel
import com.solidcapstone.semar.ui.event.EventViewModel
import com.solidcapstone.semar.ui.event.ticket.TicketViewModel
import com.solidcapstone.semar.ui.home.HomeViewModel
import com.solidcapstone.semar.ui.profile.edit.EditProfileViewModel
import com.solidcapstone.semar.ui.scan.ScanViewModel

class WayangViewModelFactory private constructor(private val wayangRepository: WayangRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(wayangRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(wayangRepository) as T
        } else if (modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            return ScanViewModel(wayangRepository) as T
        } else if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(wayangRepository) as T
        } else if (modelClass.isAssignableFrom(TicketViewModel::class.java)) {
            return TicketViewModel(wayangRepository) as T
        } else if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(wayangRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: WayangViewModelFactory? = null

        fun getInstance(context: Context): WayangViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: WayangViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}