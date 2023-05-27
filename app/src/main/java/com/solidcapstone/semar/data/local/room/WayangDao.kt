package com.solidcapstone.semar.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solidcapstone.semar.data.local.entity.WayangEntity

@Dao
interface WayangDao {
    @Query("SELECT * FROM wayangs")
    fun getListWayang(): LiveData<List<WayangEntity>>

    @Query("SELECT * FROM wayangs ORDER BY RANDOM() LIMIT :limit")
    fun getListWayang(limit: Int): LiveData<List<WayangEntity>>

    @Query("SELECT * FROM wayangs WHERE `id` = :id")
    fun getWayang(id: Int): LiveData<WayangEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWayangs(wayangs: List<WayangEntity>)

    @Query("DELETE FROM wayangs")
    suspend fun deleteAllWayangs()
}