package com.example.myfoodera

data class MenuItem(
    val id: Int,
    val name: String,
    val price: String,
    val imageResource: Int,
    var isFavorite: Boolean = false
)