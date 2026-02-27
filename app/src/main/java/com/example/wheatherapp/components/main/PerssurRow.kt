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

@Composable
fun PressureRow(weatherItem: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RowItem(
            text = weatherItem.humidity.toString() + " %",
            iconRes = R.drawable.humidity
        )

        RowItem(
            text = weatherItem.pressure.toString() + " psi",
            iconRes = R.drawable.pressure
        )
        RowItem(
            text = weatherItem.humidity.toString() + " mph",
            iconRes = R.drawable.wind
        )
    }
}

