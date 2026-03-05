package com.example.wheatherapp.data.repository

import com.example.wheatherapp.data.DataOrException
import com.example.wheatherapp.data.network.WeatherApi
import com.example.wheatherapp.domain.model.Weather
import com.example.wheatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(cityQuery: String): DataOrException<Weather, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}
