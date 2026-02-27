package com.example.wheatherapp.repository

import com.example.wheatherapp.data.DataOrException
import com.example.wheatherapp.model.Weather
import com.example.wheatherapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    val api: WeatherApi
) {
    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}