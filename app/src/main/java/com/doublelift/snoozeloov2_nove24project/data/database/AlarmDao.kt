package com.doublelift.snoozeloov2_nove24project.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.doublelift.snoozeloov2_nove24project.domain.AlarmItem
import com.doublelift.snoozeloov2_nove24project.data.AndroidAlarmScheduler
import com.doublelift.snoozeloov2_nove24project.domain.Alarm
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(alarm: Alarm)

    @Update
    suspend fun update(alarm : Alarm)

    @Delete
    suspend fun delete(alarm : Alarm)

    @Query("DELETE FROM alarm_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM alarm_table")
    fun readAll(): Flow<List<Alarm>>

    @Query("SELECT * FROM alarm_table WHERE alarmID = :alarmID")
    suspend fun getAlarmWithID(alarmID: Int) : Alarm

    suspend fun activateAnAlarm(alarm: Alarm, scheduler: AndroidAlarmScheduler) {
        // activate an alarm with the Alarm Manager with the scheduler
        val now = LocalDateTime.now()
        val alarmTimeToday = LocalDateTime.of(
            LocalDate.now(),
            LocalTime.of(alarm.hours, alarm.minutes)
        )

        val adjustedAlarmTime = if (alarmTimeToday.isBefore(now)) {
            alarmTimeToday.plusDays(1)
        } else {
            alarmTimeToday
        }

        val alarmItem = AlarmItem(
            alarm.title,
            adjustedAlarmTime,
            alarm.alarmId,
        )
        scheduler.schedule(alarmItem)
    }

    suspend fun deactivateAnAlarm(alarm: Alarm, scheduler: AndroidAlarmScheduler) {

    }
}