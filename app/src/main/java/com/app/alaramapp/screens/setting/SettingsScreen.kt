package com.app.alaramapp.screens.setting


import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.net.toUri

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
fun SettingsScreen1(
    settings: AppSettings,
    onUpdateSetting: (AppSettings.() -> AppSettings) -> Unit
) {
    val context = LocalContext.current

    val ringtoneLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri: Uri? = result.data?.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            uri?.let {
                val ringtone = RingtoneManager.getRingtone(context, it)
                val title = ringtone?.getTitle(context) ?: "Unknown"
                onUpdateSetting {
                    copy(
                        alarmSound = title,
                        alarmSoundUri = it.toString()
                    )
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        SettingItem("Alarm Sound", settings.alarmSound) {
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
                putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
                putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Sound")
                putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
                putExtra(
                    RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
                    settings.alarmSoundUri.takeIf { it.isNotEmpty() }?.let { Uri.parse(it) }
                )
            }
            ringtoneLauncher.launch(intent)
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
fun SettingsScreen(
    settings: AppSettings,
    onUpdateSetting: (AppSettings.() -> AppSettings) -> Unit
) {
    val context = LocalContext.current

    val ringtoneLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri: Uri? = result.data?.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            uri?.let {
                val ringtone = RingtoneManager.getRingtone(context, it)
                val title = ringtone?.getTitle(context) ?: "Unknown"

                //  Strip URI of query params
                val cleanUri = it.toString().substringBefore("?").toUri()

                onUpdateSetting {
                    copy(
                        alarmSound = title,
                        alarmSoundUri = cleanUri.toString()
                    )
                }

                Log.d("SettingsScreen", "Selected ringtone: $title | Clean URI: $cleanUri")
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        SettingItem("Alarm Sound", settings.alarmSound) {
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
                putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
                putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Sound")
                putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
                putExtra(
                    RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
                    settings.alarmSoundUri.takeIf { it.isNotEmpty() }?.let { Uri.parse(it) }
                )
            }
            ringtoneLauncher.launch(intent)
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
    SettingsScreen(settings = AppSettings(), onUpdateSetting = {})

}



