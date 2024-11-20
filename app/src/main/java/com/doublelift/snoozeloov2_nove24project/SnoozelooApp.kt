package com.doublelift.snoozeloov2_nove24project

import android.app.Application
import com.doublelift.snoozeloov2_nove24project.di.appModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SnoozelooApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this@SnoozelooApp)
        startKoin {
            androidLogger()
            androidContext(this@SnoozelooApp)
            modules(
                appModule
            )
        }
    }
}