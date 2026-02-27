package com.example.wheatherapp.repository

import com.example.wheatherapp.data.WeatherDao
import com.example.wheatherapp.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(
    private val weatherDao: WeatherDao
) {
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorite()

    suspend fun deleteAll() = weatherDao.deleteAllFavorite()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFarvorite(favorite)
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.inserFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun getFavoriteById(city: String): Favorite? = weatherDao.getFavoriteById(city)}