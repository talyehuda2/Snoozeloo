package com.doublelift.snoozeloov2_nove24project.presentaion.alarmsList

import com.doublelift.snoozeloov2_nove24project.data.Alarm

sealed interface AlarmsListAction {
    data object OnAddAlarmClick: AlarmsListAction
    data class OnSwitchToggle(val alarmId: Int, val isActive: Boolean) : AlarmsListAction
    data class OnAlarmClick(val alarm: Alarm): AlarmsListAction
    data class OnAlarmDelete(val alarm: Alarm): AlarmsListAction
}