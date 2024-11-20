package com.doublelift.snoozeloov2_nove24project.presentaion.alarmStart


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.doublelift.snoozeloov2_nove24project.R
import com.doublelift.snoozeloov2_nove24project.presentaion.alramAdd.AlarmAddAction
import com.doublelift.snoozeloov2_nove24project.presentaion.ui.theme.SnoozeBlue
import com.doublelift.snoozeloov2_nove24project.presentaion.ui.theme.SnoozeDisableGrey
import com.doublelift.snoozeloov2_nove24project.presentaion.ui.theme.SnoozeLightGrey
import com.doublelift.snoozeloov2_nove24project.presentaion.ui.theme.SnoozelooV2Nove24ProjectTheme
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable

fun AlarmsStartScreenRoot(
    onTurnOffClick: () -> Unit,
    state: AlarmStartState,
//    viewModel: AlarmStartViewModel = koinViewModel(),
) {
    AlarmsStartScreen(
        state = state,
        onAction = { action ->
            when(action) {
                AlarmStartAction.OnTurnOffClick -> onTurnOffClick()
            }
        }
    )
}

@Composable
private fun AlarmsStartScreen(
    state: AlarmStartState,
    onAction: (AlarmStartAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.background(SnoozeLightGrey),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SnoozeLightGrey)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.alarm),
                contentDescription = null,
                tint = SnoozeBlue
            )
            Text(
                text = "${state.hours}:${state.minutes}",
                color = SnoozeBlue,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = state.title,
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge
            )
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(30.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SnoozeBlue,
                ),
                onClick = { onAction(AlarmStartAction.OnTurnOffClick) }
            ) {
                Text(
                    text = stringResource(id = R.string.turn_off),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

        }
    }
}


fun formatTimeForCard(hours: Int, minutes: Int): String {
    val uiHours = if (hours % 12 == 0) 12 else hours % 12 // Convert 0 and 12-hour formats to 12

    return String.format(Locale.getDefault(),"%02d:%02d", uiHours, minutes)
}

@Preview
@Composable
private fun AlarmsListScreenPreview() {
    SnoozelooV2Nove24ProjectTheme {
        AlarmsStartScreen(
            state = AlarmStartState(),
            onAction = {}

        )
    }
}


private fun convertTextFieldStateToInt(textFieldState: TextFieldState): Int? {
    return textFieldState.text.toString().toIntOrNull()
}
