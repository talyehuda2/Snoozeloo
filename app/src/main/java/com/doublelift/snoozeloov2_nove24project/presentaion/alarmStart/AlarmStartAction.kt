package com.doublelift.snoozeloov2_nove24project.presentaion.alarmStart

sealed interface AlarmStartAction {
    data object OnTurnOffClick : AlarmStartAction
}