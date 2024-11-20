package com.doublelift.snoozeloov2_nove24project.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.doublelift.snoozeloov2_nove24project.presentaion.alarmStart.AlarmActivity

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra("EXTRA_TITLE") ?: return
        val hours = intent.getStringExtra("EXTRA_TIME_HOURS") ?: return
        val minutes = intent.getStringExtra("EXTRA_TIME_MINUTES") ?: return
        println("Alarm Triggered message: $title")
        println("Alarm Triggered hour: $hours")
        println("Alarm Triggered minutes: $minutes")
        val alarmIntent = Intent(context, AlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("EXTRA_TITLE", title)
            putExtra(("EXTRA_TIME_HOURS"), hours)
            putExtra(("EXTRA_TIME_MINUTES"), minutes)
        }
        context?.startActivity(alarmIntent)

    }
}