package com.example.wheatherapp.data.repository

import android.content.SharedPreferences
import com.example.wheatherapp.domain.model.Settings
import com.example.wheatherapp.domain.repository.SettingsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SettingsRepository {

    private val KEY_TEMP_UNIT = "temperature_unit"
    private val KEY_IS_DARK = "is_dark_theme"

    override fun getSettings(): Flow<Settings?> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            if (key == KEY_TEMP_UNIT || key == KEY_IS_DARK) {
                trySend(readSettings(prefs))
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        
        // Emit initial value
        trySend(readSettings(sharedPreferences))

        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    override suspend fun getSettingsSuspend(): Settings? {
        return readSettings(sharedPreferences)
    }

    private fun readSettings(prefs: SharedPreferences): Settings {
        return Settings(
            temperatureUnit = prefs.getString(KEY_TEMP_UNIT, "CELSIUS") ?: "CELSIUS",
            isDarkTheme = prefs.getBoolean(KEY_IS_DARK, false)
        )
    }

    override suspend fun updateTemperatureUnit(unit: String) {
        sharedPreferences.edit().putString(KEY_TEMP_UNIT, unit).apply()
    }

    override suspend fun updateTheme(isDark: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_DARK, isDark).apply()
    }

    override suspend fun initializeSettingsIfNotExists() {
        // SharedPreferences default values handle this
    }
}
