package com.app.alaramapp.screens.home

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.app.alaramapp.data.AlarmEntity
import com.app.alaramapp.data.AlarmRepository


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.alaramapp.AlarmHelper
import com.app.alaramapp.data.WeekDay

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AlarmRepository
) : ViewModel() {

    val alarms = repository.getAlarms()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    var selectedDays = mutableStateListOf<WeekDay>()




    fun deleteAlarm(alarm: AlarmEntity) {
        viewModelScope.launch {
            repository.deleteAlarm(alarm)
            AlarmHelper.stop()

        }
    }

    fun toggleAlarm(context: Context, alarm: AlarmEntity) {
        viewModelScope.launch {
            repository.updateAlarm(alarm.copy(isEnabled = !alarm.isEnabled))
            if (alarm.isEnabled){
                AlarmHelper.scheduleAlarm(context, alarm)
            }
            else{
                AlarmHelper.cancelAlarm(context, alarm.id)
                AlarmHelper.stop()

            }
        }
    }
}
