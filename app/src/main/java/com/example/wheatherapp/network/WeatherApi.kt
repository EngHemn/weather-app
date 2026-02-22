package com.example.wheatherapp.network

import com.example.wheatherapp.model.Weather
import com.example.wheatherapp.utils.Constant
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET("data/2.5/forecast/daily?")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constant.API_KEY
    ): Weather
//    / ss
}