package com.old.leopards.restaurant.database.repositories

import com.old.leopards.restaurant.database.dao.FoodDescriptionDao
import com.old.leopards.restaurant.database.entities.FoodDescription
import kotlinx.coroutines.flow.Flow

class FoodDescriptionRepository(private val foodDescriptionDao: FoodDescriptionDao) {

    val getAllFoodDescriptions: Flow<List<FoodDescription>> =
        foodDescriptionDao.getAllFoodDescriptions()

    suspend fun addFoodDescription(foodDescription: FoodDescription) {
        foodDescriptionDao.addFoodDescription(foodDescription)
    }

}
