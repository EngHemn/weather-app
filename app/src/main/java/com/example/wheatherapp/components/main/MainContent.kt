package com.example.wheatherapp.components.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wheatherapp.model.Weather
import com.example.wheatherapp.screens.setting.SettingsViewModel
import com.example.wheatherapp.utils.formatDate
import com.example.wheatherapp.utils.formatDecimals

@Composable
fun MainContent(
    data: Weather,
    modifier: Modifier,

) {
    val imageUrl = "https://openweathermap.org/img/wn/${data.list.first().weather[0].icon}.png"
    val first = data.list.first()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val settings = settingsViewModel.settings.collectAsState()
    val unit = settings.value?.temperatureUnit ?: "CELSIUS"
    var tem = "C"
    if (unit == "CELSIUS") tem = "C"
    else tem = "F"
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top   // ✅ THIS IS THE FIX
    ) {

        Text(
            text = formatDate(data.list.first().dt),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Surface(
            modifier = Modifier
                .padding(top = 12.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFEABD22)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(first.temp.day) + "º "  + tem,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = first.weather[0].main,
                    fontStyle = FontStyle.Italic
                )
            }
        }

        PressureRow(weatherItem = first)
        HorizontalDivider()
        SunSetSunriseRow(weatherItem = first)
        Text(
            "This Week",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    top = 3.dp,
                    start = 3.dp,
                    end = 3.dp
                ),
            color = Color(0xFFE4EEE4),
            shape = RoundedCornerShape(14.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                items(data.list) { item ->
                    WeatherDetailsRow(weather = item)
                }
            }
        }
    }

}




