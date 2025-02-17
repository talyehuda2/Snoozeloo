package com.doublelift.snoozeloov2_nove24project.presentation.screens.alarmsList


import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.doublelift.snoozeloov2_nove24project.R
import com.doublelift.snoozeloov2_nove24project.domain.Alarm
import com.doublelift.snoozeloov2_nove24project.presentation.theme.Montserrat
import com.doublelift.snoozeloov2_nove24project.presentation.theme.SnoozeBlue
import com.doublelift.snoozeloov2_nove24project.presentation.theme.SnoozeLightBlue
import com.doublelift.snoozeloov2_nove24project.presentation.theme.SnoozeLightGrey
import com.doublelift.snoozeloov2_nove24project.presentation.theme.SnoozelooV2Nove24ProjectTheme
import com.doublelift.snoozeloov2_nove24project.presentation.utils.AlarmUtils.timeLeftUntil
import com.doublelift.snoozeloov2_nove24project.presentation.utils.TextFormatters.formatTimeFor12HoursFormat
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable

fun AlarmsListScreenRoot(
    onAddAlarmClick: () -> Unit,
    onAlarmClick: (Alarm) -> Unit,
    viewModel: AlarmsListViewModel = koinViewModel(),
) {
    AlarmsListScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is AlarmsListAction.OnAddAlarmClick -> onAddAlarmClick()
                is AlarmsListAction.OnAlarmClick -> onAlarmClick(action.alarm)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlarmsListScreen(
    state: AlarmsListState,
    onAction: (AlarmsListAction) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

//    val context = LocalContext.current
//    var hasPermission by remember {
//        mutableStateOf(
//            ContextCompat.checkSelfPermission(
//                context,
//                android.Manifest.permission.POST_NOTIFICATIONS
//            ) == PackageManager.PERMISSION_GRANTED
//        )
//    }

//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) {isGranted ->
////        hasPermission = isGranted
//    }

    Scaffold(
        modifier = Modifier.background(SnoozeLightGrey),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(AlarmsListAction.OnAddAlarmClick)
                },
                containerColor = SnoozeBlue,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(60.dp),

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            TopAppBar(
                {
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = stringResource(id = R.string.your_alarms),
                        fontFamily = Montserrat,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
//                .padding(bottom = 60.dp),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center
        ) {
            if (state.isAlarmsListEmpty) {
                NoAlarmsScreen()
            } else {
                AlarmsList(
                    state = state,
                    isLandscape = isLandscape,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun NoAlarmsScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.alarm),
            contentDescription = null,
            tint = SnoozeBlue
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.empty_alarms_list),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }

}

@Composable
private fun AlarmsList(
    modifier: Modifier = Modifier,
    state: AlarmsListState,
    isLandscape: Boolean = false,
    onAction: (AlarmsListAction) -> Unit
) {
    val numOfColumns = if (isLandscape) 2 else 1
    LazyVerticalGrid(
        columns = GridCells.Fixed(numOfColumns),
        modifier = modifier
            .background(SnoozeLightGrey)
            .fillMaxSize()
            .padding(16.dp)
            .then(
                if (isLandscape) Modifier else Modifier.padding(bottom = 60.dp)
            ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = state.alarms,
            key = { it.alarmId }
        ) { currentAlarm ->
            SwipeToDeleteContainer(
                item = currentAlarm,
                onDelete = { deletedAlarm ->
                    onAction(AlarmsListAction.OnAlarmDelete(deletedAlarm))
                },
                content = {
                    AlarmCard(
                        alarm = currentAlarm,
                        onCardClick = {
                            onAction(AlarmsListAction.OnAlarmClick(currentAlarm))
                        },
                        onSwitchToggle = { isToggled ->
                            onAction(AlarmsListAction.OnSwitchToggle(currentAlarm, isToggled))
                        },
                    )
                }
            )
        }
    }
}


@Composable
private fun AlarmCard(
    alarm: Alarm,
    onCardClick: () -> Unit,
    onSwitchToggle: (Boolean) -> Unit,
) {
    val timeFormatedAsText = formatTimeFor12HoursFormat(alarm.hours, alarm.minutes)
    val timeLeftUntil = timeLeftUntil(alarm.hours, alarm.minutes)

    var isSwitchedOn by remember { mutableStateOf(alarm.isActive) }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .fillMaxWidth()
            .clickable { onCardClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = alarm.title,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = timeFormatedAsText,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(10.dp))
                val amPm = if (alarm.hours > 12) "PM" else "AM"
                Text(
                    text = amPm,
                    style = MaterialTheme.typography.labelLarge

                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = timeLeftUntil,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Switch(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 8.dp, top = 8.dp),
            checked = isSwitchedOn,
            onCheckedChange = {
                isSwitchedOn = it
                onSwitchToggle(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = SnoozeBlue,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = SnoozeLightBlue,
                checkedBorderColor = Color.Transparent,
                uncheckedBorderColor = Color.Transparent,
            )

        )

    }
}


@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground(swipeToDismissBoxState = state)
            },
            content = {content(item)},
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false,
        )
    }
}

@Composable
fun DeleteBackground(
    swipeToDismissBoxState: SwipeToDismissBoxState
) {
    val color = if (swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        Color.Red
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}


@Preview
@Composable
private fun AlarmsListScreenPreview() {
    SnoozelooV2Nove24ProjectTheme {
        AlarmsList(
            state = AlarmsListState(
                listOf(
                    Alarm(
                        alarmId = 0,
                        title = "Wake Up",
                        hours = 10,
                        minutes = 12,
                        isActive = false
                    ),
                )
            ),
            onAction = {}
        )
    }
}


//fun timeLeftUntil(targetHour: Int, targetMinute: Int): String {
//    val now = LocalDateTime.now()
//    val targetTimeToday = LocalDateTime.of(now.toLocalDate(), LocalTime.of(targetHour, targetMinute))
//
//    // If the target time has already passed today, calculate for the same time tomorrow
//    val targetTime = if (targetTimeToday.isAfter(now)) targetTimeToday else targetTimeToday.plusDays(1)
//
//    val duration = Duration.between(now, targetTime)
//    val hoursLeft = duration.toHours()
//    val minutesLeft = duration.toMinutes() % 60
//
//    return when {
//        hoursLeft > 0 && minutesLeft > 0 -> "Alarm in ${hoursLeft}h ${minutesLeft}min"
//        hoursLeft == 0L -> "Alarm in ${minutesLeft}min"
//        else -> "Alarm in ${hoursLeft}h"
//    }
//}


//fun formatTimeForCard(hours: Int, minutes: Int): String {
//    val uiHours = if (hours % 12 == 0) 12 else hours % 12 // Convert 0 and 12-hour formats to 12
//    return String.format(Locale.getDefault(),"%02d:%02d", uiHours, minutes)
//}
