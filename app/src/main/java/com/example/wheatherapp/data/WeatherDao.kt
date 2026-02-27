package com.example.wheatherapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wheatherapp.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * from fvr_tbl ")
     fun getFavorite(): Flow<List<Favorite>>

    @Query("SELECT * from fvr_tbl where city = :city LIMIT 1")
    suspend fun getFavoriteById(city: String): Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)
    @Query("DELETE from fvr_tbl")
    suspend fun deleteAllFavorite()

    @Delete
    suspend fun deleteFarvorite(favorite: Favorite)
}