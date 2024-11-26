package com.doublelift.snoozeloov2_nove24project.presentaion.screens.alarmStart

//import android.media.MediaPlayer
//import android.media.Ringtone
//import android.media.RingtoneManager
//import android.os.Build
//import android.os.Bundle
//import android.view.WindowManager
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.annotation.RequiresApi
//import com.doublelift.snoozeloov2_nove24project.R
//import com.doublelift.snoozeloov2_nove24project.presentaion.theme.SnoozelooV2Nove24ProjectTheme
//import kotlin.math.min
//
//class AlarmActivity : ComponentActivity() {
//    private var ringtone: Ringtone? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//
//        setShowWhenLocked(true)
//        setTurnScreenOn(true)
//
//        val alarmSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
//        ringtone = RingtoneManager.getRingtone(this, alarmSoundUri)
//        ringtone?.apply {
//            volume = 1.0F
//        }
//        println("ringtone: $ringtone")
//        ringtone?.play()
//
//
//        val title = intent?.getStringExtra("EXTRA_TITLE")
//        val hours = intent.getStringExtra("EXTRA_TIME_HOURS")
//        val minutes = intent.getStringExtra("EXTRA_TIME_MINUTES")
//        println("AlarmActivity title: $title")
//        println("AlarmActivity hours: $hours")
//        println("AlarmActivity minutes: $minutes")
//        setContent {
//            SnoozelooV2Nove24ProjectTheme {
//                AlarmsStartScreenRoot(
//                    state = AlarmStartState(
//                        title = title ?: "",
//                        hours = hours ?: "00",
//                        minutes = minutes ?: "00"
//                    ),
//                    onTurnOffClick = { finish() }
//                )
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//
//        ringtone?.stop()
//        ringtone = null
//    }
//}
