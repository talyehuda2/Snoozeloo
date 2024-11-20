package com.doublelift.snoozeloov2_nove24project.presentaion.alarmStart

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.doublelift.snoozeloov2_nove24project.R
import com.doublelift.snoozeloov2_nove24project.presentaion.ui.theme.SnoozelooV2Nove24ProjectTheme
import kotlin.math.min

class AlarmActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_rooster_remixed)
        mediaPlayer?.start()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val title = intent?.getStringExtra("EXTRA_TITLE")
        val hours = intent.getStringExtra("EXTRA_TIME_HOURS")
        val minutes = intent.getStringExtra("EXTRA_TIME_MINUTES")
        println("AlarmActivity title: $title")
        println("AlarmActivity hours: $hours")
        println("AlarmActivity minutes: $minutes")
        setContent {
            SnoozelooV2Nove24ProjectTheme {
                AlarmsStartScreenRoot(
                    state = AlarmStartState(
                        title = title ?: "",
                        hours = hours ?: "00",
                        minutes = minutes ?: "00"
                    ),
                    onTurnOffClick = { finish() }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer?.release()
        mediaPlayer = null
    }
}
