package com.doublelift.snoozeloov2_nove24project.domain

interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}