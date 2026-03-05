package com.example.wheatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wheatherapp.presentation.screens.about.AboutScreen
import com.example.wheatherapp.presentation.screens.favorite.FavoriteScreen
import com.example.wheatherapp.presentation.screens.main.MainScreen
import com.example.wheatherapp.presentation.viewmodel.MainViewModel
import com.example.wheatherapp.presentation.screens.search.SearchScreen
import com.example.wheatherapp.presentation.screens.setting.SettingScreen
import com.example.wheatherapp.presentation.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController)
        }
        composable(
            WeatherScreens.MainScreen.name + "/{city}", arguments = listOf(
                navArgument(name = "city") { type = NavType.StringType }
            )) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    city = city?:""
                )
            }

        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController)
        }
        composable(WeatherScreens.SettingScreen.name) {
            SettingScreen(navController=navController)
        }
        composable(WeatherScreens.FavoriteScreen.name) {
            FavoriteScreen(navController=navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController=navController)
        }
    }
}




