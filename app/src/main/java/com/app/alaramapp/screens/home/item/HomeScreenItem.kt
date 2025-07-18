package com.app.alaramapp.screens.home.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.alaramapp.data.AlarmEntity
import com.app.alaramapp.ui.theme.AlaramAppTheme
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.app.alaramapp.data.WeekDay


@Composable
fun HomeScreenItem(
    alarm: AlarmEntity,
    timeFormat24Hour: Boolean,
   // clockStyle: String,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    val time = remember(alarm.hour, alarm.minute, timeFormat24Hour) {
        val hour = alarm.hour
        val minute = alarm.minute
        if (timeFormat24Hour) {
            "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
        } else {
            val isAm = hour < 12
            val amPm = if (isAm) "AM" else "PM"
            val formattedHour = if (hour % 12 == 0) 12 else hour % 12
            "${formattedHour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')} $amPm"
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            //  Conditionally display digital or analog
            Text(
                text = time,
                style = MaterialTheme.typography.headlineLarge
            )

            /*if (clockStyle == "Digital") {
                Text(
                    text = time,
                    style = MaterialTheme.typography.headlineLarge
                )
            } else {
                AnalogClock(hour = alarm.hour, minute = alarm.minute)
            }*/

            Text(
                text = alarm.daysOfWeek.joinToString(", ") { it.displayName },
                style = MaterialTheme.typography.bodySmall
            )
        }

        Switch(checked = alarm.isEnabled, onCheckedChange = { onToggle() })

        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Alarm")
        }
    }
}


@Composable
fun AnalogClock(hour: Int, minute: Int) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(MaterialTheme.colorScheme.secondary, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "🕒", // Placeholder emoji or custom drawing
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}


@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Switch(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.background,
            uncheckedThumbColor = MaterialTheme.colorScheme.background,
            checkedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
            checkedBorderColor = Color.Transparent,
            uncheckedBorderColor = Color.Transparent
        )
    )
}




@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun HomeScreenItemPreview() {
    AlaramAppTheme {
        HomeScreenItem(
            alarm = AlarmEntity(
                id = 1,
                hour = 6,
                minute = 30,
                isEnabled = true,
                daysOfWeek = listOf(WeekDay.Monday,WeekDay.Tuesday,WeekDay.Wednesday,WeekDay.Thursday,WeekDay.Friday)
            ),
            onToggle = {},
            onDelete = {},
            timeFormat24Hour = false,
            //clockStyle = "elit",
        )
    }
}


