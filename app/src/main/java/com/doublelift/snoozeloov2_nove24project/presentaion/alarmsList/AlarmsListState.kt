package com.doublelift.snoozeloov2_nove24project.presentaion.alarmsList

import com.doublelift.snoozeloov2_nove24project.data.Alarm

data class AlarmsListState(
    val alarms: List<Alarm> = emptyList(),
    val isAlarmsListEmpty: Boolean = alarms.isEmpty(),
    val isLoading: Boolean = true
)
