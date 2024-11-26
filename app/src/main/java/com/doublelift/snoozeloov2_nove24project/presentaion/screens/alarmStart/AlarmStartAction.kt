package com.doublelift.snoozeloov2_nove24project.presentaion.screens.alarmStart

sealed interface AlarmStartAction {
    data object OnTurnOffClick : AlarmStartAction
}