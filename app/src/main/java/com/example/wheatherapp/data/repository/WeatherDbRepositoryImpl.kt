package com.example.wheatherapp.data.repository

import com.example.wheatherapp.data.WeatherDao
import com.example.wheatherapp.domain.model.Favorite
import com.example.wheatherapp.domain.repository.WeatherDbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherDbRepository {
    override fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorite()
    override suspend fun deleteAll() = weatherDao.deleteAllFavorite()
    override suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFarvorite(favorite)
    override suspend fun insertFavorite(favorite: Favorite) = weatherDao.inserFavorite(favorite)
    override suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    override suspend fun getFavoriteById(city: String): Favorite? = weatherDao.getFavoriteById(city)
}
