package com.doublelift.snoozeloov2_nove24project.di

import androidx.room.Room
import com.doublelift.snoozeloov2_nove24project.data.AlarmDatabase
import com.doublelift.snoozeloov2_nove24project.domain.AlarmRepository
//import com.doublelift.snoozeloov2_nove24project.presentaion.alarmStart.AlarmStartViewModel
import com.doublelift.snoozeloov2_nove24project.presentaion.alarmsList.AlarmsListViewModel
import com.doublelift.snoozeloov2_nove24project.presentaion.alramAdd.AlarmAddViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::AlarmsListViewModel)
    viewModelOf(::AlarmAddViewModel)
//    viewModelOf(::AlarmStartViewModel)

    single {
        Room.databaseBuilder(
            androidApplication(),
            AlarmDatabase::class.java,
            "alarms_dbs"
        ).build()
    }
    single { get<AlarmDatabase>().alarmDao }
    single {
        AlarmRepository(get())
    }
}