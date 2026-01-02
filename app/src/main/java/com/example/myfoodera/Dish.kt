package com.example.myfoodera

import java.io.Serializable

data class Dish(
    val id: Int,
    val name: String,
    val price: String,
    val imageResource: Int,
    var isFavorite: Boolean = false
) : Serializable