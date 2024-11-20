package com.doublelift.snoozeloov2_nove24project.domain

import com.doublelift.snoozeloov2_nove24project.data.AlarmItem

interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}