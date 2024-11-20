package com.doublelift.snoozeloov2_nove24project.domain

import com.doublelift.snoozeloov2_nove24project.data.Alarm
import com.doublelift.snoozeloov2_nove24project.data.AlarmDao
import com.doublelift.snoozeloov2_nove24project.data.AndroidAlarmScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

class AlarmRepository(private val alarmDao: AlarmDao) {

    val getAllAlarms: Flow<List<Alarm>> = alarmDao.readAll()

    suspend fun updateAlarmStatus(alarmId: Int, isActive: Boolean) {
        val alarm = alarmDao.getAlarmWithID(alarmId)
        alarmDao.update(alarm.copy(isActive = isActive))
    }

    suspend fun insert(alarm: Alarm): Long {
        return alarmDao.insert(alarm)
    }

    suspend fun update(alarm : Alarm) {
        alarmDao.update(alarm)
    }

    suspend fun delete(alarm : Alarm) {
        alarmDao.delete(alarm)
    }

    suspend fun deleteAll() {
        alarmDao.deleteAll()
    }

    suspend fun getAlarmWithID(alarmID: Int): Alarm {
        return alarmDao.getAlarmWithID(alarmID)
    }

    suspend fun activateAlarm(alarm: Alarm, scheduler: AndroidAlarmScheduler) {
        alarmDao.activateAnAlarm(alarm, scheduler)
    }

    suspend fun deactivateAlarm(alarm: Alarm, scheduler: AndroidAlarmScheduler) {
        alarmDao.deactivateAnAlarm(alarm, scheduler)
    }

    suspend fun activateAllAlarms(scheduler: AndroidAlarmScheduler) {
        val alarmsFlow = getAllAlarms
        println("BootCompRece: 1")
        alarmsFlow.take(1).collect { alarms ->
            println("BootCompRece: 2")
            alarms.forEach { alarm ->
                println("BootCompRece: 3")
                activateAlarm(alarm, scheduler)
                println("BootCompRece: 4")
            }
        }
        println("BootCompRece: 7")
    }
}