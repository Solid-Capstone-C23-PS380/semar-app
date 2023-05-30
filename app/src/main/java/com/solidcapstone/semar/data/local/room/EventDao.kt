package com.solidcapstone.semar.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solidcapstone.semar.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getListEvent(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM events WHERE `id` = :id")
    fun getEvent(id: Int): LiveData<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Query("DELETE FROM events")
    suspend fun deleteAllEvents()
}