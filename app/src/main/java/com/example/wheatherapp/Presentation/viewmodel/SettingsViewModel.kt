package com.example.wheatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.domain.model.Settings
import com.example.wheatherapp.domain.useCase.GetSettingsUseCase
import com.example.wheatherapp.domain.useCase.InitializeSettingsUseCase
import com.example.wheatherapp.domain.useCase.UpdateThemeUseCase
import com.example.wheatherapp.domain.useCase.UpdateUnitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateUnitUseCase: UpdateUnitUseCase,
    private val updateThemeUseCase: UpdateThemeUseCase,
    private val initializeSettingsUseCase: InitializeSettingsUseCase
) : ViewModel() {

    private val _settings = MutableStateFlow<Settings?>(null)
    val settings: StateFlow<Settings?> = _settings.asStateFlow()

    private val _unitSymbol = MutableStateFlow("C")
    val unitSymbol: StateFlow<String> = _unitSymbol.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadSettings()
    }


    private fun loadSettings() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                initializeSettingsUseCase()
                getSettingsUseCase.getSettingsFlow().collect { settings ->
                    _settings.value = settings
                    _unitSymbol.value = if (settings?.temperatureUnit == "FAHRENHEIT") "F" else "C"
                    _errorMessage.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load settings: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun updateTemperatureUnit(unit: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                updateUnitUseCase(unit)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update settings: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
 
    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            try {
                updateThemeUseCase(isDark)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update theme: ${e.message}"
            }
        }
    }

    fun getCurrentUnit(): String {
        return _settings.value?.temperatureUnit ?: "CELSIUS"
    }


    fun clearError() {
        _errorMessage.value = null
    }
}
