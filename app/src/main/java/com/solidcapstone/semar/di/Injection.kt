package com.solidcapstone.semar.di

import android.content.Context
import com.solidcapstone.semar.data.local.room.WayangDatabase
import com.solidcapstone.semar.data.remote.repository.WayangRepository
import com.solidcapstone.semar.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): WayangRepository {
        val apiService = ApiConfig.getApiService()
        val database = WayangDatabase.getDatabase(context)

        return WayangRepository.getInstance(apiService, database)
    }
}