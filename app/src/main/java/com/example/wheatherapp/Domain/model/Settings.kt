package com.example.wheatherapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "settings_tbl")
data class Settings(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int = 1,

    @ColumnInfo(name = "temperature_unit")
    val temperatureUnit: String = "CELSIUS",

    @ColumnInfo(name = "is_dark_theme")
    val isDarkTheme: Boolean = false
)
