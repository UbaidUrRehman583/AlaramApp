package com.app.alaramapp.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [AlarmEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}

class Converters {

    @TypeConverter
    fun fromWeekDayList(days: List<WeekDay>): String {
        return days.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toWeekDayList(data: String): List<WeekDay> {
        return if (data.isBlank()) emptyList()
        else data.split(",").mapNotNull { name ->
            WeekDay.entries.find { it.name == name }
        }
    }
}