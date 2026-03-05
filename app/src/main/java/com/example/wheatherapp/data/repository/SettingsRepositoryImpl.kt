package com.example.wheatherapp.data.repository

import com.example.wheatherapp.data.SettingsDao
import com.example.wheatherapp.domain.model.Settings
import com.example.wheatherapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDao: SettingsDao
) : SettingsRepository {
    override fun getSettings(): Flow<Settings?> = settingsDao.getSettings()

    override suspend fun getSettingsSuspend(): Settings? = settingsDao.getSettingsSuspend()

    override suspend fun updateTemperatureUnit(unit: String) {
        val currentSettings = settingsDao.getSettingsSuspend() ?: Settings()
        val settings = currentSettings.copy(temperatureUnit = unit)
        settingsDao.updateSettings(settings)
    }

    override suspend fun updateTheme(isDark: Boolean) {
        val currentSettings = settingsDao.getSettingsSuspend() ?: Settings()
        val settings = currentSettings.copy(isDarkTheme = isDark)
        settingsDao.updateSettings(settings)
    }

    override suspend fun initializeSettingsIfNotExists() {
        val currentSettings = settingsDao.getSettingsSuspend()
        if (currentSettings == null) {
            settingsDao.insertSettings(Settings())
        }
    }
}
