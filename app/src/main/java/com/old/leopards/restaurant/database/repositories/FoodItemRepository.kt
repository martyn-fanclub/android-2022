package com.old.leopards.restaurant.database.repositories

import androidx.lifecycle.LiveData
import com.old.leopards.restaurant.database.dao.FoodItemDao
import com.old.leopards.restaurant.database.entities.FoodItem

class FoodItemRepository(private val foodItemDao: FoodItemDao) {

    val getAllFoodItems: LiveData<List<FoodItem>> = foodItemDao.getAllFoodItems()

    suspend fun addFoodItem(foodItem: FoodItem) {
        foodItemDao.addFoodItem(foodItem)
    }

}