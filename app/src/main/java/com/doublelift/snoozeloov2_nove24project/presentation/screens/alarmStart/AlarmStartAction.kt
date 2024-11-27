package com.doublelift.snoozeloov2_nove24project.presentation.screens.alarmStart

sealed interface AlarmStartAction {
    data object OnTurnOffClick : AlarmStartAction
}