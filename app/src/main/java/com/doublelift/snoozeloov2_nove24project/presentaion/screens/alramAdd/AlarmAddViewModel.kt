package com.doublelift.snoozeloov2_nove24project.presentaion.screens.alramAdd

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doublelift.snoozeloov2_nove24project.domain.Alarm
import com.doublelift.snoozeloov2_nove24project.domain.AlarmItem
import com.doublelift.snoozeloov2_nove24project.data.AndroidAlarmScheduler
import com.doublelift.snoozeloov2_nove24project.data.repository.AlarmRepositoryImpl
import com.doublelift.snoozeloov2_nove24project.presentaion.utils.AlarmUtils.toIntOrNull
import kotlinx.coroutines.launch

class AlarmAddViewModel(
    private val repository: AlarmRepositoryImpl
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
                    hours = state.hours.toIntOrNull()!!,
                    minutes = state.minutes.toIntOrNull()!!,
                    isActive = true,
                )
            )
        }
    }

//    private fun convertTextFieldStateToInt(textFieldState: TextFieldState): Int? {
//        return textFieldState.text.toString().toIntOrNull()
//    }
}