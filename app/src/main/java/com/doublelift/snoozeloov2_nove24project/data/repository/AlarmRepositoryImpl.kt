package com.doublelift.snoozeloov2_nove24project.data.repository

import com.doublelift.snoozeloov2_nove24project.data.AndroidAlarmScheduler
import com.doublelift.snoozeloov2_nove24project.data.database.AlarmDao
import com.doublelift.snoozeloov2_nove24project.domain.Alarm
import com.doublelift.snoozeloov2_nove24project.domain.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

class AlarmRepositoryImpl(private val alarmDao: AlarmDao): AlarmRepository {

    override fun getAlarms(): Flow<List<Alarm>> {
        return alarmDao.readAll()
    }

    override suspend fun insert(alarm: Alarm) {
        return alarmDao.insert(alarm)
    }

    override suspend fun delete(alarm : Alarm) {
        alarmDao.delete(alarm)
    }

    override suspend fun activateAlarm(alarm: Alarm, scheduler: AndroidAlarmScheduler) {
        alarmDao.activateAnAlarm(alarm, scheduler)
    }

    override suspend fun activateAllAlarms(scheduler: AndroidAlarmScheduler) {
        val alarmsFlow = getAlarms()
        alarmsFlow.take(1).collect { alarms ->
            alarms.forEach { alarm ->
                activateAlarm(alarm, scheduler)
            }
        }
    }

    override suspend fun updateAlarmStatus(alarm: Alarm, isActive: Boolean) {
        alarmDao.update(alarm.copy(isActive = isActive))
    }
}
