package com.doublelift.snoozeloov2_nove24project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.doublelift.snoozeloov2_nove24project.presentation.screens.alarmStart.AlarmStartState
import com.doublelift.snoozeloov2_nove24project.presentation.screens.alarmStart.AlarmsStartScreenRoot
import com.doublelift.snoozeloov2_nove24project.presentation.screens.alarmsList.AlarmsListScreenRoot
import com.doublelift.snoozeloov2_nove24project.presentation.screens.alramAdd.AlarmsAddScreenRoot
import kotlinx.serialization.Serializable

const val DEEP_LINK_DOMAIN = "double_lift.com"
@Serializable
data object ListScreen

@Serializable
data object AddScreen

@Serializable
data object AlarmActivatedScreen


@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    println("NavigationRoot: Checking deep link navigation")

    NavHost(
        navController = navController,
        startDestination = ListScreen
    ) {
        composable<ListScreen> {
            println("NavigationRoot: Navigated to ListScreen")
            AlarmsListScreenRoot(
                onAddAlarmClick = {
                    navController.navigate(AddScreen)
                },
                onAlarmClick = {
                }
            )
        }
        composable<AddScreen> {
            AlarmsAddScreenRoot(
                onSaveClick = {
                    navController.navigate(ListScreen)
                },
                onExitClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<AlarmActivatedScreen>(
            deepLinks = listOf(
                navDeepLink<AlarmActivatedScreen>(
                    basePath = "https://$DEEP_LINK_DOMAIN/{title}/{hours}/{minutes}"
                )
            )
        ) {
            println("NavigationRoot: Navigated to AlarmActivatedScreen")
            val title = it.arguments?.getString("title") ?: "Default Title"
            val hours = it.arguments?.getString("hours") ?: "00"
            val minutes = it.arguments?.getString("minutes") ?: "00"
            println("AlarmActivatedScreen: Parsed deep link arguments - Title = $title, Hours = $hours, Minutes = $minutes")
            val alarmState = AlarmStartState(
                title = title,
                hours = hours,
                minutes = minutes
            )

            AlarmsStartScreenRoot(
                onTurnOffClick = { navController.popBackStack()},
                state = alarmState
            )
        }
    }
}
