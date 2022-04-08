package com.old.leopards.restaurant.models

data class Food(
    val id: Long,
    val title: String,
    val description: String,
    val weight: Int,
    val price: Int,
    val img: Int
)