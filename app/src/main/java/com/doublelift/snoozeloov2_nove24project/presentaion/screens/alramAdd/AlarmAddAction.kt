package com.doublelift.snoozeloov2_nove24project.presentaion.screens.alramAdd

sealed interface AlarmAddAction {
    data object OnSaveClick : AlarmAddAction
    data object OnAlarmNameClick: AlarmAddAction
    data object OnTitleDialogDismiss: AlarmAddAction
    data object OnExitClick: AlarmAddAction
    data class OnTitleDialogConfirm(val title: String): AlarmAddAction
}