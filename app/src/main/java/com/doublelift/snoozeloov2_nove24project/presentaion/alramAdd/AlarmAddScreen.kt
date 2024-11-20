@file:OptIn(ExperimentalFoundationApi::class)

package com.doublelift.snoozeloov2_nove24project.presentaion.alramAdd


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.forEachChange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.doublelift.snoozeloov2_nove24project.R
import com.doublelift.snoozeloov2_nove24project.presentaion.ui.theme.SnoozeBlue
import com.doublelift.snoozeloov2_nove24project.presentaion.ui.theme.SnoozeDisableGrey
import com.doublelift.snoozeloov2_nove24project.presentaion.ui.theme.SnoozeGrey
import com.doublelift.snoozeloov2_nove24project.presentaion.ui.theme.SnoozeLightGrey
import com.doublelift.snoozeloov2_nove24project.presentaion.ui.theme.SnoozelooV2Nove24ProjectTheme
import org.koin.androidx.compose.koinViewModel
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import java.util.Locale

@Composable

fun AlarmsAddScreenRoot(
    onSaveClick: () -> Unit,
    onExitClick: () -> Unit,
    viewModel: AlarmAddViewModel = koinViewModel(),
) {
    AlarmsDetailsScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                AlarmAddAction.OnSaveClick -> onSaveClick()
                AlarmAddAction.OnExitClick -> onExitClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlarmsDetailsScreen(
    state: AlarmAddState,
    onAction: (AlarmAddAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.background(SnoozeLightGrey),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(16.dp),
                title = {},
                navigationIcon = {
                    Icon(
                        modifier = Modifier.size(40.dp)
                            .clickable { onAction(AlarmAddAction.OnExitClick) },
                        imageVector = ImageVector.vectorResource(id = R.drawable.remove_rectangle),
                        contentDescription = stringResource(id = R.string.navigate_back),
                        tint = SnoozeDisableGrey,
                    )
                },
                actions = {
                    val saveButtonContainerColor = if (state.canSave) SnoozeBlue else SnoozeDisableGrey
                    Button(
                        modifier = Modifier
                            .width(90.dp)
                            .height(40.dp)
                            .clip(RoundedCornerShape(30.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = saveButtonContainerColor,
                        ),
                        enabled = state.canSave,
                        onClick = { onAction(AlarmAddAction.OnSaveClick) }
                    ) {
                        Text(
                            text = stringResource(id = R.string.save),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column {
            PickAlarmTimeCard(
                state = state,
                modifier = Modifier.padding(paddingValues)
            )
            PickAlarmTitle(
                state = state,
                onTitleClick = {
                    onAction(AlarmAddAction.OnAlarmNameClick)
                }
            )
        }

        if (state.shouldShowDialog) {
            val temporaryTitle by remember { mutableStateOf(state.alarmTitle) }
            AlertDialog(
                containerColor = Color.White,
                onDismissRequest = {
                    // Dismiss the dialog when tapped outside or back pressed
                    onAction(AlarmAddAction.OnTitleDialogDismiss)
                },
                confirmButton = {
                    Button(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SnoozeBlue,
                        ),
                        onClick = {
                            onAction(AlarmAddAction.OnTitleDialogConfirm(temporaryTitle.toString()))
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.save),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                },
                title = {
                    Text(
                        text = "Alarm Name",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                text = {
                    Column {
                        BasicTextField(
                            state = state.alarmTitle,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(SnoozeLightGrey)
                                .padding(horizontal = 8.dp, vertical = 12.dp),
                            textStyle = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun PickAlarmTimeCard(
    state: AlarmAddState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp, end = 16.dp, start = 16.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
//        val hoursLocalState by remember { mutableStateOf(TextFieldState()) }
//        val minutesLocalState by remember { mutableStateOf(TextFieldState()) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberCardWithUpperLimit(number = state.hours, 23)
            Text(":", color = SnoozeGrey)
            NumberCardWithUpperLimit(number = state.minutes, 59)
        }
        if (
            state.hours.toIntOrNull() != null
            &&
            state.minutes.toIntOrNull() != null
            ){
            Text(
                text = timeLeftUntil(
                    state.hours.toIntOrNull()!!,
                    state.minutes.toIntOrNull()!!
                ),
                style = MaterialTheme.typography.bodySmall,
                color = SnoozeGrey
            )
        }
    }
}
@Composable
private fun PickAlarmTitle(
    state: AlarmAddState,
    modifier: Modifier = Modifier,
    onTitleClick: () -> Unit
) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
                .clickable { onTitleClick() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.alarm_name),
                style = MaterialTheme.typography.bodyMedium,

                )
            Text(
                text = state.alarmTitle.text.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = SnoozeGrey
            )
        }
}

@Composable
private fun NumberCardWithUpperLimit(
    number: TextFieldState,
    upperLimit: Int,
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .size(width = 128.dp, height = 95.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(SnoozeLightGrey),
        contentAlignment = Alignment.Center,
    ) {
        BasicTextField(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 29.dp),
            state = number,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            textStyle = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center
            ),
            inputTransformation = HourOrMinuteInputTransformation(upperLimit)
        )
    }
}


class HourOrMinuteInputTransformation(private val upperLimit: Int): InputTransformation {
    override fun TextFieldBuffer.transformInput() {
        if (asCharSequence().length > 2) {
            replace(0, asCharSequence().length, asCharSequence().subSequence(0,2))
        }

        val intText = asCharSequence().toString().toIntOrNull()
        intText?.let {
            if (intText !in 0..upperLimit) {
                replace(0, length, upperLimit.toString())
            }
        }

        changes.forEachChange { range, _ ->
            if (!range.collapsed) {
                val newText = asCharSequence().substring(range.start, range.end)
                val filteredText = newText.filter { it.isDigit() }
                if (newText != filteredText) {
                    replace(range.start, range.end, filteredText)
                }
            }
        }
    }
}



fun timeLeftUntil(targetHour: Int, targetMinute: Int): String {
    val now = LocalDateTime.now()
    val targetTimeToday = LocalDateTime.of(now.toLocalDate(), LocalTime.of(targetHour, targetMinute))

    // If the target time has already passed today, calculate for the same time tomorrow
    val targetTime = if (targetTimeToday.isAfter(now)) targetTimeToday else targetTimeToday.plusDays(1)

    val duration = Duration.between(now, targetTime)
    val hoursLeft = duration.toHours()
    val minutesLeft = duration.toMinutes() % 60

    if (hoursLeft.toInt() != 0 && minutesLeft.toInt() != 0) {
        return "Alarm in ${hoursLeft}h ${minutesLeft}min"
    }
    if (hoursLeft.toInt() == 0) {
        return "Alarm in ${minutesLeft}min"
    }
    return "Alarm in ${hoursLeft}h"
}


@Preview
@Composable
private fun AlarmsListScreenPreview() {
    SnoozelooV2Nove24ProjectTheme {
        AlarmsDetailsScreen(
            state = AlarmAddState(
                alarmTitle = TextFieldState("New Alarm"),
                canSave = true,
            ),
            onAction = {}
        )
    }
}


private fun TextFieldState.toIntOrNull(): Int? {
    return this.text.toString().toIntOrNull()
}
