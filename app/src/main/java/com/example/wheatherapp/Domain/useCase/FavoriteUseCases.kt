package com.example.wheatherapp.domain.useCase

import com.example.wheatherapp.domain.model.Favorite
import com.example.wheatherapp.domain.repository.WeatherDbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: WeatherDbRepository
) {
    operator fun invoke(): Flow<List<Favorite>> = repository.getFavorites()
}

class AddFavoriteUseCase @Inject constructor(
    private val repository: WeatherDbRepository
) {
    suspend operator fun invoke(favorite: Favorite) = repository.insertFavorite(favorite)
}

class DeleteFavoriteUseCase @Inject constructor(
    private val repository: WeatherDbRepository
) {
    suspend operator fun invoke(favorite: Favorite) = repository.deleteFavorite(favorite)
}
