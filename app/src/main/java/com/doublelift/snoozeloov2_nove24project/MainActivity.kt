package com.doublelift.snoozeloov2_nove24project

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.doublelift.snoozeloov2_nove24project.presentaion.screens.alarmsList.AlarmsListViewModel
import com.doublelift.snoozeloov2_nove24project.presentaion.navigation.NavigationRoot
import com.doublelift.snoozeloov2_nove24project.presentaion.theme.SnoozelooV2Nove24ProjectTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<AlarmsListViewModel>()

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        this.turnScreenOnAndKeyguardOff()

        setShowWhenLocked(true)
        setTurnScreenOn(true)
        with(getSystemService(KEYGUARD_SERVICE) as KeyguardManager) {
            requestDismissKeyguard(this@MainActivity, null)
        }

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.state.isLoading
            }
        }

        println("MainActivity: onCreate Intent Data = ${intent?.data}")

        setContent {
            SnoozelooV2Nove24ProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navController = rememberNavController()
                    NavigationRoot(
                        navController = navController
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}


fun Activity.turnScreenOnAndKeyguardOff() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        setShowWhenLocked(true)
        setTurnScreenOn(true)
    } else {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )
    }
    with(getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestDismissKeyguard(this@turnScreenOnAndKeyguardOff, null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SnoozelooV2Nove24ProjectTheme {
    }
}
