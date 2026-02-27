//package com.example.wheatherapp.data
//
//class DataOrException<T, Boolean, E : Exception>(
//    val data: T? = null,
//    var loading: Boolean?=null,
//    val e: E? = null
//)
package com.example.wheatherapp.data

class DataOrException<T, E : Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null
)
class DataOrExceptionDB<T, L , E : Exception>(
    var data: T? = null,
    var loading: L? = null,
    var e: E? = null
)