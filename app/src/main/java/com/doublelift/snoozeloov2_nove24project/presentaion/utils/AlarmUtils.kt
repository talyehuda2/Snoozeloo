package com.doublelift.snoozeloov2_nove24project.presentaion.utils

import androidx.compose.foundation.text.input.TextFieldState
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

object AlarmUtils {
    fun timeLeftUntil(targetHour: Int, targetMinute: Int): String {
        val now = LocalDateTime.now()
        val targetTimeToday = LocalDateTime.of(now.toLocalDate(), LocalTime.of(targetHour, targetMinute))

        // If the target time has already passed today, calculate for the same time tomorrow
        val targetTime = if (targetTimeToday.isAfter(now)) targetTimeToday else targetTimeToday.plusDays(1)

        val duration = Duration.between(now, targetTime)
        val hoursLeft = duration.toHours()
        val minutesLeft = duration.toMinutes() % 60

        return when {
            hoursLeft > 0 && minutesLeft > 0 -> "Alarm in ${hoursLeft}h ${minutesLeft}min"
            hoursLeft == 0L -> "Alarm in ${minutesLeft}min"
            else -> "Alarm in ${hoursLeft}h"
        }
    }

    fun TextFieldState.toIntOrNull(): Int? {
        return this.text.toString().toIntOrNull()
    }
}