package com.solidcapstone.semar.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solidcapstone.semar.data.local.entity.WayangEntity
import com.solidcapstone.semar.helper.Converters

@Database(
    entities = [WayangEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class WayangDatabase : RoomDatabase() {
    abstract fun wayangDao(): WayangDao

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