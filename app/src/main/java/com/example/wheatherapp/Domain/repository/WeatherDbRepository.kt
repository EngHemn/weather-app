package com.example.wheatherapp.domain.repository

import com.example.wheatherapp.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface WeatherDbRepository {
    fun getFavorites(): Flow<List<Favorite>>
    suspend fun deleteAll()
    suspend fun deleteFavorite(favorite: Favorite)
    suspend fun insertFavorite(favorite: Favorite)
    suspend fun updateFavorite(favorite: Favorite)
    suspend fun getFavoriteById(city: String): Favorite?
}
