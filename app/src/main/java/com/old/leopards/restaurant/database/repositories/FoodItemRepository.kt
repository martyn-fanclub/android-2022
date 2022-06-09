package com.old.leopards.restaurant.database.repositories

import com.old.leopards.restaurant.database.dao.FoodItemDao
import com.old.leopards.restaurant.database.entities.FoodItem
import kotlinx.coroutines.flow.Flow

class FoodItemRepository(private val foodItemDao: FoodItemDao) {

    val getAllFoodItems: Flow<List<FoodItem>> = foodItemDao.getAllFoodItems()

    suspend fun addFoodItem(foodItem: FoodItem) {
        foodItemDao.addFoodItem(foodItem)
    }

}
