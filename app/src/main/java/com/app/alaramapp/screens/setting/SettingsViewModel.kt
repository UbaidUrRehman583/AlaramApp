package com.app.alaramapp.screens.setting

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.alaramapp.data.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    init {
        viewModelScope.launch {
            SettingsDataStore.getSettings(context).collect {
                _settings.value = it
            }
        }
    }

    fun updateSetting(update: AppSettings.() -> AppSettings) {
        val newSettings = _settings.value.update()
        _settings.value = newSettings
        viewModelScope.launch {
            SettingsDataStore.saveSettings(context, newSettings)
        }
    }
}


data class AppSettings(
    val clockStyle: String = "Digital",
    val alarmSound: String = "Default",
    val snoozeDuration: String = "10 minutes",
    val timeFormat: String = "12-hour",
    val autoSilence: String = "Never"
)

