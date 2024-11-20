package com.doublelift.snoozeloov2_nove24project.presentaion.alarmStart
//
//import androidx.compose.foundation.text.input.TextFieldState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.doublelift.snoozeloov2_nove24project.data.Alarm
//import com.doublelift.snoozeloov2_nove24project.domain.AlarmRepository
//import kotlinx.coroutines.launch
//
//class AlarmStartViewModel(
//    private val repository: AlarmRepository
//): ViewModel() {
//    var state by mutableStateOf(AlarmStartState())
//        private set
////
////        fun onAction(action: AlarmStartAction) {
////        when (action) {
////            AlarmStartAction.OnTurnOffClick -> TODO()
////        }
////    }
//
//    private fun convertTextFieldStateToInt(textFieldState: TextFieldState): Int? {
//        return textFieldState.text.toString().toIntOrNull()
//    }
//}