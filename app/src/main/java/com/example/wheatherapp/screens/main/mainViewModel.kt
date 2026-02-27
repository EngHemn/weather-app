package com.example.wheatherapp.screens.main

import androidx.lifecycle.ViewModel
import com.example.wheatherapp.data.DataOrException
import com.example.wheatherapp.model.Weather
import com.example.wheatherapp.repository.SettingsRepository
import com.example.wheatherapp.repository.WeatherRepository
import com.example.wheatherapp.utils.TemperatureConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    /**
     * Get weather data and apply temperature conversion if needed
     */
    suspend fun getWeather(city: String): DataOrException<Weather, Exception> {
        return try {
            val weatherData = repository.getWeather(cityQuery = city)
            
            // Apply temperature conversion if Fahrenheit is selected
            if (weatherData.data != null) {
                val settings = settingsRepository.getSettingsSuspend()
                val temperatureUnit = settings?.temperatureUnit ?: "CELSIUS"
                
                if (temperatureUnit == "FAHRENHEIT") {
                    // Convert all temperatures in the weather data
                    val convertedWeather = convertWeatherData(weatherData.data!!, temperatureUnit)
                    DataOrException(data = convertedWeather, e = null, loading = false)
                } else {
                    weatherData
                }
            } else {
                weatherData
            }
        } catch (e: Exception) {
            DataOrException(e = e)
        }
    }

    /**
     * Convert all temperature values in weather data from Celsius to Fahrenheit
     */
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

    /**
     * Get current temperature unit preference
     */
    suspend fun getTemperatureUnit(): String {
        return settingsRepository.getSettingsSuspend()?.temperatureUnit ?: "CELSIUS"
    }
}