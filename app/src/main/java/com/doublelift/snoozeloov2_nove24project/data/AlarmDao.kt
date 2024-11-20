package com.doublelift.snoozeloov2_nove24project.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.LocalTime

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(alarm: Alarm): Long

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
        println("BootCompRece: 5")
        val now = LocalDateTime.now()
        val alarmItem = AlarmItem(
            alarm.title,
            LocalDateTime.of(now.toLocalDate(), LocalTime.of(alarm.hours.toString().toInt(), alarm.minutes.toString().toInt())),
        )
        alarmItem.let(scheduler::schedule)
        println("BootCompRece: 6")

    }

    suspend fun deactivateAnAlarm(alarm: Alarm, scheduler: AndroidAlarmScheduler) {

    }
}