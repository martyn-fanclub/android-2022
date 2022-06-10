package com.old.leopards.restaurant.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_items")
data class FoodItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val portionSize: Int,
    val price: Int,
    val photoLink: String
)
