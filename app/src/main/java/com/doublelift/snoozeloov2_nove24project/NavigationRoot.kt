package com.doublelift.snoozeloov2_nove24project

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.doublelift.snoozeloov2_nove24project.presentaion.alarmsList.AlarmsListScreenRot
import com.doublelift.snoozeloov2_nove24project.presentaion.alramAdd.AlarmsAddScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "alarm"
    ) {
        alarmGraph(navController)
    }
}


private fun NavGraphBuilder.alarmGraph(navController: NavHostController) {
    navigation(
        startDestination = "list",
        route ="alarm"
    ) {
        composable(route = "list") {
            AlarmsListScreenRot(
                onAddAlarmClick = {
                    navController.navigate("add")
                },
                onAlarmClick = {
//                    navController.navigate("add")
                }
            )
        }
        composable(route = "add") {
            AlarmsAddScreenRoot(
                onSaveClick = {
                    navController.navigate("list")
                },
                onExitClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}