package com.example.wheatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wheatherapp.domain.model.Favorite
import com.example.wheatherapp.domain.model.Settings

@Database(
    entities = [Favorite::class, Settings::class],
    version = 3,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun settingsDao(): SettingsDao
}
