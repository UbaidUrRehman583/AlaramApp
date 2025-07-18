package com.app.alaramapp.data

// DataStoreExtensions.kt

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.app.alaramapp.screens.setting.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

object SettingsDataStore {

    private val CLOCK_STYLE = stringPreferencesKey("clock_style")
    private val ALARM_SOUND = stringPreferencesKey("alarm_sound")
    private val SNOOZE_DURATION = stringPreferencesKey("snooze_duration")
    private val TIME_FORMAT = stringPreferencesKey("time_format")
    private val AUTO_SILENCE = stringPreferencesKey("auto_silence")

    suspend fun saveSettings(context: Context, settings: AppSettings) {
        context.dataStore.edit {
         //   it[CLOCK_STYLE] = settings.clockStyle
            it[ALARM_SOUND] = settings.alarmSound
            it[SNOOZE_DURATION] = settings.snoozeDuration
            it[TIME_FORMAT] = settings.timeFormat
            it[AUTO_SILENCE] = settings.autoSilence
        }
    }

    fun getSettings(context: Context): Flow<AppSettings> {
        return context.dataStore.data.map { prefs ->
            AppSettings(
               // clockStyle = prefs[CLOCK_STYLE] ?: "Digital",
                alarmSound = prefs[ALARM_SOUND] ?: "Default",
                snoozeDuration = prefs[SNOOZE_DURATION] ?: "10 minutes",
                timeFormat = prefs[TIME_FORMAT] ?: "24-hour",
                autoSilence = prefs[AUTO_SILENCE] ?: "Never"
            )
        }
    }
}
