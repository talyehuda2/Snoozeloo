package com.doublelift.snoozeloov2_nove24project.presentaion.utils

import java.util.Locale

object TextFormatters {
    fun String.addZeroPrefix(): String {
        return if (this.length == 1) {
            "0$this"
        } else {
            this
        }
    }

    fun formatTimeFor12HoursFormat(hours: Int, minutes: Int): String {
        val uiHours = if (hours % 12 == 0) 12 else hours % 12 // Convert 0 and 12-hour formats to 12
        return String.format(Locale.getDefault(),"%02d:%02d", uiHours, minutes)
    }

}