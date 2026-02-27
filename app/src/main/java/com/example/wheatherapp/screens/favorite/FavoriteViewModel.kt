package com.example.wheatherapp.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.model.Favorite
import com.example.wheatherapp.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: WeatherDbRepository
) : ViewModel() {
    val _feild = MutableStateFlow<List<Favorite>>(emptyList())
    val favlist = _feild.asStateFlow()
    val favorite = mutableSetOf<Favorite>()
    init {
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged().collect { list ->
                _feild.value = list
            }
        }
    }

    fun inserFav(favorite: Favorite) = viewModelScope.launch {
        repository.insertFavorite(favorite)
    }
    fun updateFav(favorite: Favorite) = viewModelScope.launch {
        repository.updateFavorite(favorite)
    }
    fun deleteFav(favorite: Favorite) = viewModelScope.launch {
        Log.d("sdffdffff" , "sdfdgfdgf")
        repository.deleteFavorite(favorite)
        getFavorites()
    }

    fun deleteAllFav() = viewModelScope.launch {
        repository.deleteAll()
    }
    fun getFavById(city:String) = viewModelScope.launch {
        val data = repository.getFavoriteById(city)
        if (data != null) favorite.add(data)
    }
}