package com.doublelift.snoozeloov2_nove24project.domain

import com.doublelift.snoozeloov2_nove24project.data.database.AlarmDao
import com.doublelift.snoozeloov2_nove24project.data.AndroidAlarmScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

interface AlarmRepository {
    fun getAlarms(): Flow<List<Alarm>>
    suspend fun insert(alarm: Alarm)
    suspend fun delete(alarm : Alarm)
    suspend fun activateAlarm(alarm: Alarm, scheduler: AndroidAlarmScheduler)
    suspend fun activateAllAlarms(scheduler: AndroidAlarmScheduler)
    suspend fun updateAlarmStatus(alarm: Alarm, isActive: Boolean)
}