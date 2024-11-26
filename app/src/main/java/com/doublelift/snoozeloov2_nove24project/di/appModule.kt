package com.doublelift.snoozeloov2_nove24project.di

import androidx.room.Room
import com.doublelift.snoozeloov2_nove24project.presentaion.app.SnoozelooApp
import com.doublelift.snoozeloov2_nove24project.data.database.AlarmDatabase
import com.doublelift.snoozeloov2_nove24project.data.repository.AlarmRepositoryImpl
//import com.doublelift.snoozeloov2_nove24project.presentaion.alarmStart.AlarmStartViewModel
import com.doublelift.snoozeloov2_nove24project.presentaion.screens.alarmsList.AlarmsListViewModel
import com.doublelift.snoozeloov2_nove24project.presentaion.screens.alramAdd.AlarmAddViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::AlarmsListViewModel)
    viewModelOf(::AlarmAddViewModel)

    single { SnoozelooApp() }

    single {
        Room.databaseBuilder(
            androidApplication(),
            AlarmDatabase::class.java,
            "alarms_dbs"
        ).build()
    }
    single { get<AlarmDatabase>().alarmDao }
    single {
        AlarmRepositoryImpl(get())
    }
}