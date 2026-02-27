package com.example.wheatherapp.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.wheatherapp.model.Favorite
import com.example.wheatherapp.navigation.WeatherScreens
import com.example.wheatherapp.widget.WeatherAppBara

@Composable
fun FavoriteScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val vm: FavoriteViewModel = hiltViewModel()
    val data by vm.favlist.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            WeatherAppBara(
                title = "Favorite",
                isMainScreen = false,
                icon = Icons.Default.ArrowBack,
                onButtonClicked = { navController.popBackStack() } // or your action
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LazyColumn(
                ) {
                    items(data) { city ->
                        CityRow(city = city, navController = navController, vFav = vm)
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(city: Favorite, navController: NavHostController, vFav: FavoriteViewModel) {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name+"/${city.city}")
            },
        shape = CircleShape.copy(
            topEnd = CornerSize(6.dp),
        ),
        color = Color(0xFFB2DFDB)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = city.city, modifier = Modifier.padding(start = 4.dp))
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFD1E3E1)
            ) {
                Text(
                    text = city.country,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            IconButton(onClick = {
                vFav.deleteFav(city)
            }) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                    tint = Color.Red.copy(0.3f)
                )
            }
        }
    }
}