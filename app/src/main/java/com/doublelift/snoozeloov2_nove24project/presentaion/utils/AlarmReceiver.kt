package com.doublelift.snoozeloov2_nove24project.presentaion.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import com.doublelift.snoozeloov2_nove24project.presentaion.navigation.DEEP_LINK_DOMAIN
import com.doublelift.snoozeloov2_nove24project.MainActivity
import com.doublelift.snoozeloov2_nove24project.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra("EXTRA_ID", 0) ?: return
        val title = intent.getStringExtra("EXTRA_TITLE") ?: return
        val hours = intent.getStringExtra("EXTRA_TIME_HOURS") ?: return
        val minutes = intent.getStringExtra("EXTRA_TIME_MINUTES") ?: return

        val alarmIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            data = "https://$DEEP_LINK_DOMAIN/$title/$hours/$minutes".toUri()
        }

        context?.startActivity(alarmIntent)

        context?.let {
            showNotification(
                context = context,
                id = id,
                title = title,
                hours = hours,
                minutes = minutes
            )
        }
    }

    private fun showNotification(
        context: Context,
        id: Int,
        title: String,
        hours: String,
        minutes: String,
    ) {
        val alarmActivityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            data = "https://$DEEP_LINK_DOMAIN/$title/$hours/$minutes".toUri()
        }
        val alarmPendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(alarmActivityIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification = NotificationCompat.Builder(context,"alarm_channel_id")
            .setSmallIcon(R.drawable.alarm)
            .setContentTitle(
                title.ifEmpty { "Alarm" }
            )
            .setContentText("$hours:$minutes")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(alarmPendingIntent)
            .setFullScreenIntent(alarmPendingIntent, true)
            .build()

        val notificationManager = context.getSystemService<NotificationManager>()!!
        // checks that the app on background and the screen is still on
        if (!isAppInForeground() && context.isScreenOn()) {
            notificationManager.notify(id, notification)
        }
    }


    private fun Context.isScreenOn(): Boolean {
        return getSystemService(PowerManager::class.java).isInteractive
    }

    private fun isAppInForeground(): Boolean {
        return ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
    }
}