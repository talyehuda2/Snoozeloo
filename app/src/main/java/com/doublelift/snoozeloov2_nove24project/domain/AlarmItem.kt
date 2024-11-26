package com.doublelift.snoozeloov2_nove24project.domain

import java.time.LocalDateTime

data class AlarmItem(
    val title: String,
    val time: LocalDateTime,
    val id: Int
)
