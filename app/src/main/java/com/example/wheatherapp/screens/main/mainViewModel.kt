package com.example.wheatherapp.screens.main

import androidx.lifecycle.ViewModel
import com.example.wheatherapp.data.dataOrException
import com.example.wheatherapp.model.Weather
import com.example.wheatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    suspend fun getWeather(city: String): dataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city)
    }
//    val data: MutableState<dataOrException<Weather, Boolean, Exception>> =
//        mutableStateOf(dataOrException(null, loading = true))

//    init {
//        loadWeather()
//    }

//    fun loadWeather() {
//        getWeather(city = "erbil")
//    }

//    private fun getWeather(city: String) {
//        viewModelScope.launch {
//            if (city.isEmpty()) return@launch
//            data.value?.loading = true
//            data.value =repository.getWeather(city)
////            data.value.data =repository.getWeather(city) error
//
//            if (data.value.data.toString().isNotEmpty()) data.value?.loading = false
//
//        }
//    }

}