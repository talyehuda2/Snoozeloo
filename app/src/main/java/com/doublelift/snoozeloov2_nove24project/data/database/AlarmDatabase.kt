package com.doublelift.snoozeloov2_nove24project.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.doublelift.snoozeloov2_nove24project.domain.Alarm

@Database(entities = [Alarm::class], version = 1)
abstract class AlarmDatabase : RoomDatabase() {
    abstract val alarmDao: AlarmDao // Provide AlarmDao

    companion object {
        @Volatile
        private var INSTANCE: AlarmDatabase? = null

        fun getInstance(context: Context): AlarmDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlarmDatabase::class.java,
                    "alarms_dbs"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
