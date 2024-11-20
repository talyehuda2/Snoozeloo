package com.doublelift.snoozeloov2_nove24project.presentaion.alramAdd

import android.content.Context
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doublelift.snoozeloov2_nove24project.data.Alarm
import com.doublelift.snoozeloov2_nove24project.data.AlarmItem
import com.doublelift.snoozeloov2_nove24project.data.AndroidAlarmScheduler
import com.doublelift.snoozeloov2_nove24project.domain.AlarmRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class AlarmAddViewModel(
    private val repository: AlarmRepository
): ViewModel() {

    var state by mutableStateOf(AlarmAddState())
        private set

    private var scheduler: AndroidAlarmScheduler? = null
    private var alarmItem: AlarmItem? = null

    init {
        viewModelScope.launch {
            snapshotFlow { state.hours.text to state.minutes.text }
                .collect { (hours, minutes) ->
                    val canSave = !(hours.isEmpty() || minutes.isEmpty())
                    state = state.copy(
                        canSave = canSave
                    )
                }
        }
    }


    fun onAction(action: AlarmAddAction) {
        when (action) {
            is AlarmAddAction.OnSaveClick -> {
                saveAlarm(state)
            }
            is AlarmAddAction.OnAlarmNameClick -> {
                state = state.copy(
                    shouldShowDialog = true
                )
            }
            is AlarmAddAction.OnTitleDialogDismiss -> {
                state = state.copy(
                    shouldShowDialog = false
                )
            }
            is AlarmAddAction.OnTitleDialogConfirm -> {
               state = state.copy(
                   shouldShowDialog = false
               )
            }
            else -> {}
        }
    }

    private fun saveAlarm(state: AlarmAddState) {
        viewModelScope.launch {
            repository.insert(
                Alarm(
                    title = state.alarmTitle.text.toString(),
                    hours = convertTextFieldStateToInt(state.hours)!!,
                    minutes = convertTextFieldStateToInt(state.minutes)!!,
                    isActive = true,
                )
            )
        }

//        repository.activateAlarm()
        // activate an alarm with the Alarm Manager with the scheduler
//        val scheduler = AndroidAlarmScheduler(context)
//
//        val now = LocalDateTime.now()
//        alarmItem = AlarmItem(
//            state.alarmTitle.text.toString(),
//            LocalDateTime.of(now.toLocalDate(), LocalTime.of(state.hours.text.toString().toInt(), state.minutes.text.toString().toInt())),
//        )
//        alarmItem?.let(scheduler::schedule)
    }

    private fun convertTextFieldStateToInt(textFieldState: TextFieldState): Int? {
        return textFieldState.text.toString().toIntOrNull()
    }
}