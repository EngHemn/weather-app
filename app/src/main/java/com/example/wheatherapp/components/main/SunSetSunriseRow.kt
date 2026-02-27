package com.example.wheatherapp.components.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wheatherapp.R
import com.example.wheatherapp.model.WeatherItem
import com.example.wheatherapp.utils.formatDate

@Composable
fun SunSetSunriseRow(weatherItem: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RowItem(
            text = formatDate(weatherItem.sunrise),
            iconRes = R.drawable.sunrise
        )

        RowItem(
            text = formatDate(weatherItem.sunset),
            iconRes = R.drawable.sunset
        )

    }
}