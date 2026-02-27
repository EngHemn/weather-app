package com.example.wheatherapp.screens.main

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wheatherapp.components.main.MainScaffold
import com.example.wheatherapp.data.DataOrException
import com.example.wheatherapp.model.Weather

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String
) {
    val weatherData =
        produceState<DataOrException<Weather, Exception>>(
            initialValue = DataOrException(data = null, e = null, loading = true),
            key1 = Unit
        ) {
            value = mainViewModel.getWeather(city = city)
        }.value

    when {
        weatherData.loading == true -> {
            CircularProgressIndicator()
        }

        weatherData.e != null -> {
            Text(text = weatherData.e.toString())
        }

        weatherData.data != null -> {
            MainScaffold(weatherData.data !!, navController)
        }

        else -> {
            Text(text = "No data")
        }
    }
}