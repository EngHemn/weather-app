package com.example.wheatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wheatherapp.model.Favorite
import com.example.wheatherapp.model.Settings

@Database(
    entities = [Favorite::class, Settings::class],
    version = 2,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun settingsDao(): SettingsDao
}