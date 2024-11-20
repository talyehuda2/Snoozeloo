package com.doublelift.snoozeloov2_nove24project.presentaion.alarmsList

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doublelift.snoozeloov2_nove24project.data.Alarm
import com.doublelift.snoozeloov2_nove24project.data.AndroidAlarmScheduler
import com.doublelift.snoozeloov2_nove24project.domain.AlarmRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlarmsListViewModel(
    context: Context,
    private val repository: AlarmRepository
): ViewModel() {
    var state by mutableStateOf(AlarmsListState())
        private set

    init {
        viewModelScope.launch {
            repository.getAllAlarms.collectLatest { alarms ->
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
                toggleAlarmIsActive(action.alarmId,action.isActive)
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
    println("Alarm to delete: $alarm")
    }

    private fun toggleAlarmIsActive(alarmId: Int, isActive: Boolean) {
        val updatedAlarms = state.alarms.map { alarm ->
            if (alarm.alarmId == alarmId) {
                alarm.copy(isActive =  isActive)
            } else {
                alarm
            }
        }
        state = state.copy(
            alarms = updatedAlarms,
            isAlarmsListEmpty = updatedAlarms.isEmpty()
        )

        viewModelScope.launch {
            repository.updateAlarmStatus(alarmId, isActive)
        }
    }
}