package com.example.wheatherapp.components.main

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wheatherapp.model.Favorite
import com.example.wheatherapp.model.Weather
import com.example.wheatherapp.navigation.WeatherScreens
import com.example.wheatherapp.screens.favorite.FavoriteViewModel
import com.example.wheatherapp.widget.WeatherAppBara

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val favorites = favoriteViewModel.favlist.collectAsState(initial = emptyList()).value
    val isFav = favorites.any { it.city == weather.city.name }
    val context = LocalContext.current
    val showIt = remember { mutableStateOf<Boolean?>(null) }
    Scaffold(
        topBar = {
            WeatherAppBara(
                title = weather.city.name + " - " + weather.city.country,
                isMainScreen = true,
                isFavorite = isFav,
                onAddActionClicked = { navController.navigate(route = WeatherScreens.SearchScreen.name) },
                onButtonClicked = { navController.popBackStack() },
//                icon = Icons.Filled.ArrowBack,
                onFavoriteToggle = {
                    if (isFav) {
                        showIt.value = false
                        favoriteViewModel.deleteFav(
                            Favorite(
                                weather.city.name,
                                weather.city.country
                            )
                        )
                    } else {
                        showIt.value = true
                        favoriteViewModel.inserFav(
                            Favorite(
                                weather.city.name,
                                weather.city.country
                            )
                        )
                    }
                },
                onNavigate = { screen -> navController.navigate(screen) }
            )
        }
    ) { innerPadding ->
        MainContent(
            data = weather,
            modifier = Modifier.padding(innerPadding)
        )
        if (showIt.value != null)
            ShowToast(context = context, showIt)

    }
}

@Composable
fun ShowToast(
    context: Context,
    showIt: MutableState<Boolean?>
) {
    LaunchedEffect(showIt.value) {
        if (showIt.value != null) {
            val message =
                if (showIt.value == true) "Add to favorites"
                else "Remove from favorites"

            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}
