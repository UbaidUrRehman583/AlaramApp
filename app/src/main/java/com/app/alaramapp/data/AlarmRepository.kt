package com.app.alaramapp.data


import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface AlarmRepository {
    fun getAlarms(): Flow<List<AlarmEntity>>
    suspend fun addAlarm(alarm: AlarmEntity)
    suspend fun deleteAlarm(alarm: AlarmEntity)
    suspend fun updateAlarm(alarm: AlarmEntity)
}

@Singleton
class AlarmRepositoryImpl @Inject constructor(
    private val dao: AlarmDao
) : AlarmRepository {
    override fun getAlarms(): Flow<List<AlarmEntity>> = dao.getAllAlarms()
    override suspend fun addAlarm(alarm: AlarmEntity) = dao.insertAlarm(alarm)
    override suspend fun deleteAlarm(alarm: AlarmEntity) = dao.deleteAlarm(alarm)
    override suspend fun updateAlarm(alarm: AlarmEntity) = dao.updateAlarm(alarm)
}
