package com.example.wheatherapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wheatherapp.domain.model.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * from settings_tbl WHERE id = 1 LIMIT 1")
    fun getSettings(): Flow<Settings?>

    @Query("SELECT * from settings_tbl WHERE id = 1 LIMIT 1")
    suspend fun getSettingsSuspend(): Settings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: Settings)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(settings: Settings)

    @Query("DELETE from settings_tbl")
    suspend fun deleteAllSettings()
}
