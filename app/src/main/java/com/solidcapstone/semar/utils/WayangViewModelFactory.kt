package com.solidcapstone.semar.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solidcapstone.semar.data.remote.repository.WayangRepository
import com.solidcapstone.semar.di.Injection
import com.solidcapstone.semar.ui.home.HomeViewModel

class WayangViewModelFactory private constructor(private val wayangRepository: WayangRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(wayangRepository) as T
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