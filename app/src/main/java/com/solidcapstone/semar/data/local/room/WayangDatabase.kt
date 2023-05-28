package com.solidcapstone.semar.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solidcapstone.semar.data.local.entity.VideoEntity
import com.solidcapstone.semar.data.local.entity.WayangEntity
import com.solidcapstone.semar.helper.Converters

@Database(
    entities = [WayangEntity::class, VideoEntity::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class WayangDatabase : RoomDatabase() {
    abstract fun wayangDao(): WayangDao
    abstract fun videoDao(): VideoDao
    companion object {
        @Volatile
        private var INSTANCE: WayangDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): WayangDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WayangDatabase::class.java, "semar_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}