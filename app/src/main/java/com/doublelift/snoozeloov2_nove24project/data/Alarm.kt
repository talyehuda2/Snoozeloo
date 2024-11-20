package com.doublelift.snoozeloov2_nove24project.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_table")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var alarmId: Int = 0,

    val title: String,
    val hours: Int,
    val minutes: Int,
    val isActive: Boolean,
)