package com.example.wheatherapp.domain.useCase

import com.example.wheatherapp.data.DataOrException
import com.example.wheatherapp.domain.model.Weather
import com.example.wheatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): DataOrException<Weather, Exception> {
        return repository.getWeather(cityQuery = city)
    }
}
