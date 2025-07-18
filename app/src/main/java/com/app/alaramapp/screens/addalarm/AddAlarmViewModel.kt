package com.app.alaramapp.screens.addalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.alaramapp.AlarmHelper
import com.app.alaramapp.AlarmReceiver
import com.app.alaramapp.data.AlarmEntity
import com.app.alaramapp.data.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddAlarmViewModel @Inject constructor(
    private val repository: AlarmRepository
) : ViewModel() {


    fun addAlarm(context: Context, data: AlarmEntity) {
        viewModelScope.launch {
            repository.addAlarm(data)
            if (data.isEnabled) {
                AlarmHelper.scheduleAlarm(context, data)
            }
        }
    }

}




