package com.example.wheatherapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.domain.model.Favorite
import com.example.wheatherapp.domain.model.Weather
import com.example.wheatherapp.data.DataOrException
import com.example.wheatherapp.domain.useCase.AddFavoriteUseCase
import com.example.wheatherapp.domain.useCase.DeleteFavoriteUseCase
import com.example.wheatherapp.domain.useCase.GetFavoritesUseCase
import com.example.wheatherapp.domain.useCase.GetSettingsUseCase
import com.example.wheatherapp.domain.useCase.GetWeatherUseCase
import com.example.wheatherapp.domain.utils.TemperatureConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _weatherData = MutableStateFlow<DataOrException<Weather, Exception>>(
        DataOrException(data = null, e = null, loading = true)
    )
    val weatherData = _weatherData.asStateFlow()

    private val _unitSymbol = MutableStateFlow("C")
    val unitSymbol = _unitSymbol.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage = _toastMessage.asStateFlow()

    /**
     * Load weather data and apply temperature conversion if needed
     */
    fun loadWeather(city: String) {
        viewModelScope.launch {
            _weatherData.value = DataOrException(loading = true)
            try {
                val weatherResult = getWeatherUseCase(city)
                
                if (weatherResult.data != null) {
                    val settings = getSettingsUseCase.getSettingsSuspend()
                    val temperatureUnit = settings?.temperatureUnit ?: "CELSIUS"
                    _unitSymbol.value = if (temperatureUnit == "FAHRENHEIT") "F" else "C"
                    
                    updateFavoriteStatus(weatherResult.data!!.city.name)

                    if (temperatureUnit == "FAHRENHEIT") {
                        val convertedWeather = convertWeatherData(weatherResult.data!!, temperatureUnit)
                        _weatherData.value = DataOrException(data = convertedWeather, e = null, loading = false)
                    } else {
                        _weatherData.value = weatherResult
                    }
                } else {
                    _weatherData.value = weatherResult
                }
            } catch (e: Exception) {
                _weatherData.value = DataOrException(e = e, loading = false)
            }
        }
    }

    private fun convertWeatherData(weather: Weather, unit: String): Weather {
        return weather.copy(
            list = weather.list.map { item ->
                item.copy(
                    temp = item.temp.copy(
                        day = TemperatureConverter.convertTemperature(item.temp.day, unit),
                        eve = TemperatureConverter.convertTemperature(item.temp.eve, unit),
                        max = TemperatureConverter.convertTemperature(item.temp.max, unit),
                        min = TemperatureConverter.convertTemperature(item.temp.min, unit),
                        morn = TemperatureConverter.convertTemperature(item.temp.morn, unit),
                        night = TemperatureConverter.convertTemperature(item.temp.night, unit)
                    ),
                    feels_like = item.feels_like.copy(
                        day = TemperatureConverter.convertTemperature(item.feels_like.day, unit),
                        eve = TemperatureConverter.convertTemperature(item.feels_like.eve, unit),
                        morn = TemperatureConverter.convertTemperature(item.feels_like.morn, unit),
                        night = TemperatureConverter.convertTemperature(item.feels_like.night, unit)
                    )
                )
            }
        )
    }

    fun toggleFavorite(weather: Weather) {
        viewModelScope.launch {
            val favorite = Favorite(city = weather.city.name, country = weather.city.country)
            if (_isFavorite.value) {
                deleteFavoriteUseCase(favorite)
                _isFavorite.value = false
                _toastMessage.value = "Removed from favorites"
            } else {
                addFavoriteUseCase(favorite)
                _isFavorite.value = true
                _toastMessage.value = "Added to favorites"
            }
        }
    }

    private fun updateFavoriteStatus(cityName: String) {
        viewModelScope.launch {
            getFavoritesUseCase().distinctUntilChanged().collect { list ->
                _isFavorite.value = list.any { it.city == cityName }
            }
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}
