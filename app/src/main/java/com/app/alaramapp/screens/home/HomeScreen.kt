package com.app.alaramapp.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.alaramapp.data.AlarmEntity
import com.app.alaramapp.screens.home.item.HomeScreenItem
import com.app.alaramapp.screens.setting.AppSettings
import com.app.alaramapp.screens.setting.SettingsViewModel

@Composable
fun HomeScreenNavigation(
    onAddAlarmClick: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
     settingViewModel: SettingsViewModel = hiltViewModel()
){
    val alarms by viewModel.alarms.collectAsState()
    val context  = LocalContext.current

    val settings by settingViewModel.settings.collectAsState()
    HomeScreen(
        alarms,
        onAddAlarmClick = {onAddAlarmClick.invoke()},
        onToggleAlarm = {viewModel.toggleAlarm(context,it)},
        onDeleteAlarm = {viewModel.deleteAlarm(it)},
        settings = settings
    )
}



@Composable
fun HomeScreen(
    alarms: List<AlarmEntity>,
    settings: AppSettings,
    onAddAlarmClick: () -> Unit = {},
    onToggleAlarm: (AlarmEntity) -> Unit = {},
    onDeleteAlarm: (AlarmEntity) -> Unit = {},
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddAlarmClick) {
                Text("+")
            }
        }
    ) { padding ->
        if (alarms.isEmpty()) {
            // Show message when list is empty
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No current alarms",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // Show alarms
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(alarms) {
                    HomeScreenItem(
                        alarm = it,
                        clockStyle = settings.clockStyle,
                        timeFormat24Hour = settings.timeFormat == "24-hour",
                        onToggle = { onToggleAlarm(it) },
                        onDelete = { onDeleteAlarm(it) }
                    )
                }
            }
        }
    }
}




@Composable
@Preview
fun HomeScreenPreview(){
    HomeScreen(onAddAlarmClick = {},
        alarms = listOf(AlarmEntity(id = 1712, hour = 1579,
            minute = 1237,
            isEnabled = false
        ),
            AlarmEntity(id = 1712, hour = 1579,
                minute = 1237,
                isEnabled = true
            ),
            ),
        onToggleAlarm = {},
        onDeleteAlarm = {},
        settings = AppSettings(),)

}