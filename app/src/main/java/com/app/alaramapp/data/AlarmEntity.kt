package com.app.alaramapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hour: Int,
    val minute: Int,
    val soundUri: String="",
    val isEnabled: Boolean = true,
    val isRepeating: Boolean = false,
    val daysOfWeek: List<WeekDay> = emptyList()

)

enum class WeekDay(val displayName: String) {
    Sunday("Sun"),
    Monday("Mon"),
    Tuesday("Tue"),
    Wednesday("Wed"),
    Thursday("Thu"),
    Friday("Fri"),
    Saturday("Sat")
}