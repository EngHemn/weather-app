package com.example.wheatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.domain.model.Favorite
import com.example.wheatherapp.domain.useCase.AddFavoriteUseCase
import com.example.wheatherapp.domain.useCase.DeleteFavoriteUseCase
import com.example.wheatherapp.domain.useCase.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {
    private val _favoriteList = MutableStateFlow<List<Favorite>>(emptyList())
    val favlist = _favoriteList.asStateFlow()
    
    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoritesUseCase().collect { list ->
                _favoriteList.value = list
            }
        }
    }

    fun inserFav(favorite: Favorite) = viewModelScope.launch {
        addFavoriteUseCase(favorite)
    }

    fun deleteFav(favorite: Favorite) = viewModelScope.launch {
        deleteFavoriteUseCase(favorite)
    }
}
