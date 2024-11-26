package com.doublelift.snoozeloov2_nove24project.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.doublelift.snoozeloov2_nove24project.data.repository.AlarmRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BootCompletedReceiver: BroadcastReceiver(), KoinComponent {

    private val alarmRepository: AlarmRepositoryImpl by inject()
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val appContext = context?.applicationContext
            val isAppContextNull = appContext == null
            appContext?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    alarmRepository.activateAllAlarms(AndroidAlarmScheduler(appContext))
                }
            }
        }
    }
}