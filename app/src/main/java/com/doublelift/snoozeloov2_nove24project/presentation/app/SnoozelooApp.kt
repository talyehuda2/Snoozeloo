package com.doublelift.snoozeloov2_nove24project.presentation.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.content.getSystemService
import com.doublelift.snoozeloov2_nove24project.di.appModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SnoozelooApp: Application() {
    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        AndroidThreeTen.init(this@SnoozelooApp)
        startKoin {
            androidLogger()
            androidContext(this@SnoozelooApp)
            modules(
                appModule
            )
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "alarm_channel_id",
            "Snoozeloo Alarm",
            NotificationManager.IMPORTANCE_HIGH
        ). apply {
            setBypassDnd(true)
        }

        val notificationManager = getSystemService<NotificationManager>()!!
        notificationManager.createNotificationChannel(channel)
    }
}