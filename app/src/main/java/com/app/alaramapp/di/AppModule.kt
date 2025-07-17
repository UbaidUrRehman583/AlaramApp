package com.app.alaramapp.di

import com.app.alaramapp.data.AlarmDao
import com.app.alaramapp.data.AlarmDatabase
import com.app.alaramapp.data.AlarmRepository
import com.app.alaramapp.data.AlarmRepositoryImpl


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AlarmDatabase =
        Room.databaseBuilder(
            context,
            AlarmDatabase::class.java,
            "alarm_db"
        ).build()

    @Provides
    fun provideDao(db: AlarmDatabase): AlarmDao = db.alarmDao()

    @Provides
    fun provideRepository(dao: AlarmDao): AlarmRepository = AlarmRepositoryImpl(dao)
}
