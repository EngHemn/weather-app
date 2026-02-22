package com.example.wheatherapp.data

class dataOrException<T, Boolean, E : Exception>(
    val data: T? = null,
    var loading: Boolean?=null,
    val e: E? = null
)