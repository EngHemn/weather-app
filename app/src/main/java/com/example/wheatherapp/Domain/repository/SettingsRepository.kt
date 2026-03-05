package com.example.wheatherapp.domain.repository

import com.example.wheatherapp.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<Settings?>
    suspend fun getSettingsSuspend(): Settings?
    suspend fun updateTemperatureUnit(unit: String)
     suspend fun updateTheme(isDark: Boolean)
    suspend fun initializeSettingsIfNotExists()
}
