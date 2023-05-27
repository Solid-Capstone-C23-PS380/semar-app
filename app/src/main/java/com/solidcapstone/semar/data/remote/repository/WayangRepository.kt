package com.solidcapstone.semar.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.solidcapstone.semar.data.Result
import com.solidcapstone.semar.data.local.entity.WayangEntity
import com.solidcapstone.semar.data.local.room.WayangDatabase
import com.solidcapstone.semar.data.remote.retrofit.ApiService


class WayangRepository private constructor(
    private val apiService: ApiService,
    private val database: WayangDatabase,
) {
    fun getListWayang(showLimit: Int? = null) = liveData {
        emit(Result.Loading)
        try {
            val listWayangResponse = apiService.getListWayang()

            // Save with DAO
            val listWayang = listWayangResponse.map { wayangResponse ->
                WayangEntity(
                    wayangResponse.id,
                    wayangResponse.name,
                    wayangResponse.photoUrl,
                    wayangResponse.description,
                )
            }
            database.wayangDao().deleteAllWayangs()
            database.wayangDao().insertWayangs(listWayang)
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }

        // Get wayang list from local database
        val localData: LiveData<Result<List<WayangEntity>>> = if (showLimit != null) {
            database.wayangDao().getWayang(showLimit).map { Result.Success(it) }
        } else {
            database.wayangDao().getWayang().map { Result.Success(it) }
        }
        emitSource(localData)
    }

    companion object {
        @Volatile
        private var instance: WayangRepository? = null

        fun getInstance(
            apiService: ApiService,
            database: WayangDatabase,
        ): WayangRepository = instance ?: synchronized(this) {
            instance ?: WayangRepository(apiService, database)
        }.also { instance = it }
    }
}