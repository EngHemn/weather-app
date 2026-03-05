package com.example.wheatherapp.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wheatherapp.presentation.components.main.MainScaffold
import com.example.wheatherapp.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String
) {
    val weatherData = mainViewModel.weatherData.collectAsState().value

    LaunchedEffect(key1 = city) {
        mainViewModel.loadWeather(city)
    }

    Column (
        modifier = Modifier.padding(10.dp).fillMaxHeight().fillMaxWidth()
        ,
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally

    ){


    when {
        weatherData.loading == true -> {
            CircularProgressIndicator()
        }

        weatherData.e != null -> {
            Text(text = weatherData.e.toString())
        }

        weatherData.data != null -> {
            MainScaffold(weatherData.data !!, navController, mainViewModel)
        }

        else -> {
            Text(text = "No data")
        }
    }}
}
