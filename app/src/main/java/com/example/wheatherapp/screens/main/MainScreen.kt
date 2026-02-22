package com.example.wheatherapp.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wheatherapp.data.dataOrException
import com.example.wheatherapp.model.Weather
import com.example.wheatherapp.widgets.WeatherAppBar

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val weatherData =
        produceState<dataOrException<Weather, Boolean, Exception>>(
            initialValue = dataOrException(loading = true),
            key1 = Unit
        ) {
            value = mainViewModel.getWeather("Erbil")
        }.value

    when {
        weatherData.loading == true -> {
            CircularProgressIndicator()
        }

        weatherData.e != null -> {
            Text(text = weatherData.e.toString())
        }

        weatherData.data != null -> {
            MainScaffold(weatherData.data, navController) // ✅ no !!
        }

        else -> {
            Text(text = "No data")
        }
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city.name + " - " + weather.city.country,
                navController = navController,
                elevation = 5.dp,
                isMainScreen = true,
                onAddActionClicked = {},
                onButtonClicked = { navController.popBackStack() }, // ✅ back works
//                icon = Icons.Filled.ArrowBack,
            )
        }
    ) { innerPadding ->
        MainContent(
            data = weather,
            modifier = Modifier.padding(innerPadding)
        )
        Text(text = "hi")
    }
}

@Composable
fun MainContent(data: Weather, modifier: Modifier) {
    Text(text = "hi")
}