package com.doublelift.snoozeloov2_nove24project.presentation.screens.alarmsList

import com.doublelift.snoozeloov2_nove24project.domain.Alarm

sealed interface AlarmsListAction {
    data object OnAddAlarmClick: AlarmsListAction
    data class OnSwitchToggle(val alarm: Alarm, val isActive: Boolean) : AlarmsListAction
    data class OnAlarmClick(val alarm: Alarm): AlarmsListAction
    data class OnAlarmDelete(val alarm: Alarm): AlarmsListAction
}