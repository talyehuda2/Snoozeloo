package com.doublelift.snoozeloov2_nove24project.presentation.screens.alarmsList

import com.doublelift.snoozeloov2_nove24project.domain.Alarm

data class AlarmsListState(
    val alarms: List<Alarm> = emptyList(),
    val isAlarmsListEmpty: Boolean = alarms.isEmpty(),
    val isLoading: Boolean = true
)
