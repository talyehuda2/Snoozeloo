package com.doublelift.snoozeloov2_nove24project.presentation.screens.alarmStart


import android.media.RingtoneManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.doublelift.snoozeloov2_nove24project.R
import com.doublelift.snoozeloov2_nove24project.presentation.theme.SnoozeBlue
import com.doublelift.snoozeloov2_nove24project.presentation.theme.SnoozeLightGrey
import com.doublelift.snoozeloov2_nove24project.presentation.theme.SnoozelooV2Nove24ProjectTheme
import com.doublelift.snoozeloov2_nove24project.presentation.utils.TextFormatters.addZeroPrefix

@Composable

fun AlarmsStartScreenRoot(
    onTurnOffClick: () -> Unit,
    state: AlarmStartState,
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
    val context = LocalContext.current
    val ringtone = remember { RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)) }

    LaunchedEffect(Unit) {
        ringtone.volume = 1.0f
        ringtone.play()
    }

    DisposableEffect(Unit) {
        onDispose {
            ringtone.stop()
        }
    }

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
                text = "${state.hours.addZeroPrefix()}:${state.minutes.addZeroPrefix()}",
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