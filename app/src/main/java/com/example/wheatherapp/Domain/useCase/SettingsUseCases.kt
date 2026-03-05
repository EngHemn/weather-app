package com.example.wheatherapp.domain.useCase

import com.example.wheatherapp.domain.model.Settings
import com.example.wheatherapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    fun getSettingsFlow(): Flow<Settings?> = repository.getSettings()
    suspend fun getSettingsSuspend(): Settings? = repository.getSettingsSuspend()
}

class UpdateUnitUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(unit: String) = repository.updateTemperatureUnit(unit)
}

class UpdateThemeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(isDark: Boolean) = repository.updateTheme(isDark)
}

class InitializeSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke() = repository.initializeSettingsIfNotExists()
}
