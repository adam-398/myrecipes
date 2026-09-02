package dev.auroralaboratories.myrecipes.uicomponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.anhaki.picktime.PickHourMinute

@Composable
fun AuroraDurationPicker(
    totalMinutes: Int?,
    onDurationChange: (Int) -> Unit,
) {
    var hour by remember { mutableStateOf((totalMinutes ?: 0) / 60) }
    var minute by remember { mutableStateOf((totalMinutes ?: 0) % 60) }

    PickHourMinute(
        initialHour = hour,
        onHourChange = { newHour ->
            hour = newHour
            onDurationChange(hour * 60 + minute)
        },
        initialMinute = minute,
        onMinuteChange = { newMinute ->
            minute = newMinute
            onDurationChange(hour * 60 + minute)
        }
    )
}