package com.example.wheatherapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wheatherapp.data.SettingsDao
import com.example.wheatherapp.data.WeatherDao
import com.example.wheatherapp.data.WeatherDatabase
import com.example.wheatherapp.network.WeatherApi
import com.example.wheatherapp.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
        weatherDatabase.weatherDao()

    @Provides
    @Singleton
    fun provideSettingsDao(weatherDatabase: WeatherDatabase): SettingsDao =
        weatherDatabase.settingsDao()

    @Provides
    @Singleton
    fun provideWeatherDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather-database"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build().create(WeatherApi::class.java)
    }
}