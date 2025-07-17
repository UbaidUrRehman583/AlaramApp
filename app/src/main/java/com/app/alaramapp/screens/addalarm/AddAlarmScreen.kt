package com.app.alaramapp.screens.addalarm


 import android.content.res.Configuration
 import androidx.compose.foundation.clickable
 import androidx.compose.foundation.layout.Arrangement
 import androidx.compose.foundation.layout.PaddingValues
 import androidx.compose.foundation.layout.Row
 import androidx.compose.foundation.layout.Spacer
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.foundation.layout.fillMaxWidth
 import androidx.compose.foundation.layout.height
 import androidx.compose.foundation.layout.heightIn
 import androidx.compose.foundation.layout.padding
 import androidx.compose.foundation.lazy.LazyColumn
 import androidx.compose.foundation.lazy.grid.GridCells
 import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
 import androidx.compose.foundation.lazy.grid.items
 import androidx.compose.material3.Button
 import androidx.compose.material3.ButtonDefaults
 import androidx.compose.material3.Checkbox
 import androidx.compose.material3.ExperimentalMaterial3Api
 import androidx.compose.material3.MaterialTheme
 import androidx.compose.material3.Text
 import androidx.compose.material3.TimePicker
 import androidx.compose.material3.TimePickerDefaults
 import androidx.compose.material3.TimePickerLayoutType
 import androidx.compose.material3.rememberTimePickerState
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.mutableStateListOf
 import androidx.compose.runtime.remember
 import androidx.compose.runtime.snapshots.SnapshotStateList
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.platform.LocalContext
 import androidx.compose.ui.tooling.preview.Preview
 import androidx.compose.ui.unit.dp
 import androidx.hilt.navigation.compose.hiltViewModel
 import com.app.alaramapp.data.AlarmEntity
 import com.app.alaramapp.data.WeekDay

@Composable
fun AlarmScreenNavigation(
    onBack: () -> Unit,
    viewModel: AddAlarmViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    AddAlarmScreen(
        onAddAlarm = { data ->
            viewModel.addAlarm(context,data)
        },
        onBack = { onBack.invoke()
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmScreen(
    onAddAlarm: (AlarmEntity) -> Unit,
    onBack: () -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = 8,
        initialMinute = 30,
        is24Hour = false
    )
    val selectedDays = remember { mutableStateListOf<WeekDay>() }

    val hour = timePickerState.hour
    val minute = timePickerState.minute
    val isAm = hour < 12
    val amPm = if (isAm) "AM" else "PM"
    val formattedHour = if (hour % 12 == 0) 12 else hour % 12
    val formattedTime = "${formattedHour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')} $amPm"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TimePicker(
                state = timePickerState,
                modifier = Modifier.fillMaxWidth(),
                colors = TimePickerDefaults.colors(),
                layoutType = TimePickerLayoutType.Vertical,
            )
        }

        item {
            Text("Repeat On:", style = MaterialTheme.typography.titleMedium)
        }

        item {
            WeekDayGrid(selectedDays)
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                enabled = if (formattedTime.isNotEmpty()) true else false,
                onClick = {
                    val alarm = AlarmEntity(
                        id = 0,
                        hour = timePickerState.hour,
                        minute = timePickerState.minute,
                        isEnabled = true,
                        isRepeating = selectedDays.isNotEmpty(),
                        daysOfWeek = selectedDays.toList()
                    )
                    onAddAlarm(alarm)
                    onBack()
                }
            ) {
                Text("Save Alarm")
            }
        }
    }
}
@Composable
fun WeekDayGrid(selectedDays: SnapshotStateList<WeekDay>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 250.dp), // allow space for all items
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(WeekDay.entries.toList()) { day ->
            val isSelected = selectedDays.contains(day)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (isSelected) selectedDays.remove(day) else selectedDays.add(day)
                    }
                    .padding(4.dp)
            ) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = {
                        if (it) selectedDays.add(day) else selectedDays.remove(day)
                    }
                )
                Text(
                    text = day.name,
                    modifier = Modifier.padding(start = 4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
@Preview
fun AddAlarmPreview() {
    AddAlarmScreen(onAddAlarm = {}, onBack = {})

}