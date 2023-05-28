package com.solidcapstone.semar.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solidcapstone.semar.data.local.entity.VideoEntity

@Dao
interface VideoDao {

    @Query("SELECT * FROM videos")
    fun getListVideo(): LiveData<List<VideoEntity>>

    @Query("SELECT * FROM videos ORDER BY RANDOM() LIMIT :limit")
    fun getListVideo(limit: Int): LiveData<List<VideoEntity>>

    @Query("SELECT * FROM videos WHERE `id` = :id")
    fun getVideo(id: Int): LiveData<VideoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoEntity>)

    @Query("DELETE FROM videos")
    suspend fun deleteAllVideos()
}