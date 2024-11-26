package com.doublelift.snoozeloov2_nove24project.presentaion.screens.alramAdd

import androidx.compose.foundation.text.input.TextFieldState

data class AlarmAddState(
    val alarmTitle: TextFieldState = TextFieldState(),
    val hours: TextFieldState = TextFieldState("00"),
    val minutes: TextFieldState = TextFieldState("00"),
    val shouldShowDialog: Boolean = false,
    val canSave: Boolean = true,
)
