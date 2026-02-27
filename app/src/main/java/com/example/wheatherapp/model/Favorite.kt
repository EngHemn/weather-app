package com.example.wheatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull


@Entity(tableName = "fvr_tbl")
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name = "city")      // also add name =
    val city: String,

    @ColumnInfo(name = "country")   // also add name =
    val country: String
)

