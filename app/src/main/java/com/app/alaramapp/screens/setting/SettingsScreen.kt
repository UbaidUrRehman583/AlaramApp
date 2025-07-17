package com.app.alaramapp.screens.setting


import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingNavigation(viewModel: SettingsViewModel = hiltViewModel()) {
    val settings by viewModel.settings.collectAsState()

    SettingsScreen(
        settings = settings,
        onUpdateSetting = { update ->
            viewModel.updateSetting(update)
        }
    )
}
@Composable
fun SettingsScreen(
    settings: AppSettings,
    onUpdateSetting: (AppSettings.() -> AppSettings) -> Unit
) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        SettingItem("Clock Style", settings.clockStyle) {
            onUpdateSetting {
                copy(clockStyle = if (clockStyle == "Digital") "Analog" else "Digital")
            }
        }

        SettingItem("Alarm Sound", settings.alarmSound) {
            Toast.makeText(context, "Sound picker not implemented", Toast.LENGTH_SHORT).show()
        }

        SettingItem("Snooze Duration", settings.snoozeDuration) {
            onUpdateSetting {
                copy(
                    snoozeDuration = when (snoozeDuration) {
                        "5 minutes" -> "10 minutes"
                        "10 minutes" -> "15 minutes"
                        else -> "5 minutes"
                    }
                )
            }
        }

        SettingItem("Time Format", settings.timeFormat) {
            onUpdateSetting {
                copy(timeFormat = if (timeFormat == "24-hour") "12-hour" else "24-hour")
            }
        }

        SettingItem("Auto Silence", settings.autoSilence) {
            onUpdateSetting {
                copy(
                    autoSilence = when (autoSilence) {
                        "Never" -> "5 minutes"
                        "5 minutes" -> "10 minutes"
                        else -> "Never"
                    }
                )
            }
        }
    }
}


@Composable
fun SettingItem(title: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = MaterialTheme.typography.bodyLarge)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
@Preview
fun AddAlarmPreview() {
    SettingsScreen(settings = AppSettings(), onUpdateSetting = {},)

}



