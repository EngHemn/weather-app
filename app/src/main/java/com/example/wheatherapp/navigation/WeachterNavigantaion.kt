package com.example.wheatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wheatherapp.screens.main.MainScreen
import com.example.wheatherapp.screens.main.MainViewModel
import com.example.wheatherapp.screens.search.WeatherSearchScreen
import com.example.wheatherapp.screens.splash.WeatherSpashScreen

@Composable
fun WeatherNavigantaion() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name // ✅ start with Splash
    ) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSpashScreen(navController)
        }
        composable(WeatherScreens.MainScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController , mainViewModel = mainViewModel)
        }

        composable(WeatherScreens.SearchScreen.name) {   // ✅ add SearchScreen destination
            WeatherSearchScreen(navController)
        }
    }
}