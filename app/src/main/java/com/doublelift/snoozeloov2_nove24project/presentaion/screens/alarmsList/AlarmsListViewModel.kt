package com.doublelift.snoozeloov2_nove24project.presentaion.screens.alarmsList

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doublelift.snoozeloov2_nove24project.domain.Alarm
import com.doublelift.snoozeloov2_nove24project.domain.AlarmItem
import com.doublelift.snoozeloov2_nove24project.data.AndroidAlarmScheduler
import com.doublelift.snoozeloov2_nove24project.data.repository.AlarmRepositoryImpl
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class AlarmsListViewModel(
    private val context: Context,
    private val repository: AlarmRepositoryImpl,
): ViewModel() {
    var state by mutableStateOf(AlarmsListState())
        private set

    init {
        viewModelScope.launch {
            repository.getAlarms().collect { alarms ->
                state = state.copy(
                    alarms = alarms,
                    isAlarmsListEmpty = alarms.isEmpty(),
                    isLoading = false
                )
                println("Alarm to delete: ${alarms.size}")

                alarms.forEach { alarm ->
                    repository.activateAlarm(alarm, AndroidAlarmScheduler(context))
                }
            }
        }
    }

    fun onAction(action: AlarmsListAction) {
        when(action) {
            is AlarmsListAction.OnSwitchToggle -> {
                toggleAlarmIsActive(action.alarm,action.isActive)
            }
            is AlarmsListAction.OnAlarmDelete -> {
                deleteAlarm(action.alarm)
            }
            else -> Unit
        }
    }

    private fun deleteAlarm(alarm: Alarm) {
        viewModelScope.launch {
            repository.delete(alarm)
        }
    }

    private fun toggleAlarmIsActive(currentAlarm: Alarm, isActive: Boolean) {
        val updatedAlarms = state.alarms.map { alarm ->
            if (alarm.alarmId == currentAlarm.alarmId) {
                alarm.copy(isActive =  isActive)
            } else {
                alarm
            }
        }
        state = state.copy(
            alarms = updatedAlarms,
            isAlarmsListEmpty = updatedAlarms.isEmpty()
        )

        if (!isActive) {
            val scheduler = AndroidAlarmScheduler(context)
            val itemToCancel = AlarmItem(
                currentAlarm.title,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(currentAlarm.hours, currentAlarm.minutes)),
                currentAlarm.alarmId
            )
            scheduler.cancel(itemToCancel)
        }

        viewModelScope.launch {
            repository.updateAlarmStatus(currentAlarm, isActive)
        }
    }
}