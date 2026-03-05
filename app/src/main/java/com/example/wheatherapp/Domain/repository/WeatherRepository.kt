package com.example.wheatherapp.domain.repository

import com.example.wheatherapp.data.DataOrException
import com.example.wheatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Exception>
}
