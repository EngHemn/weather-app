package com.example.wheatherapp.presentation.components.main

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wheatherapp.domain.model.Weather
import com.example.wheatherapp.presentation.navigation.WeatherScreens
import com.example.wheatherapp.presentation.viewmodel.MainViewModel
import com.example.wheatherapp.presentation.viewmodel.SettingsViewModel
import com.example.wheatherapp.presentation.widget.WeatherAppBar

@Composable
fun MainScaffold(
    weather: Weather, 
    navController: NavController,
    mainViewModel: MainViewModel,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val isFav by mainViewModel.isFavorite.collectAsState()
    val settings by settingsViewModel.settings.collectAsState()
    val context = LocalContext.current
    val toastMessage by mainViewModel.toastMessage.collectAsState()

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city.name + " - " + weather.city.country,
                isMainScreen = true,
                isFavorite = isFav,
                isDarkTheme = settings?.isDarkTheme ?: false,
                onAddActionClicked = { navController.navigate(route = WeatherScreens.SearchScreen.name) },
                onFavoriteToggle = {
                    mainViewModel.toggleFavorite(weather)
                },
                onThemeToggle = { isDark ->
                    settingsViewModel.toggleTheme(isDark)
                },
                onNavigate = { screen -> navController.navigate(screen) }
            )
        }
    ) { innerPadding ->
        MainContent(
            data = weather,
            modifier = Modifier.padding(innerPadding),
            mainViewModel = mainViewModel
        )
        
        toastMessage?.let {
            ShowToast(context = context, message = it) {
                mainViewModel.clearToast()
            }
        }
    }
}

@Composable
fun ShowToast(
    context: Context,
    message: String,
    onDismiss: () -> Unit
) {
    LaunchedEffect(message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        onDismiss()
    }
}

