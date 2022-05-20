package com.old.leopards.restaurant.database.repositories

import androidx.lifecycle.LiveData
import com.old.leopards.restaurant.database.dao.FoodDescriptionDao
import com.old.leopards.restaurant.database.entities.FoodDescription

class FoodDescriptionRepository(private val foodDescriptionDao: FoodDescriptionDao) {

    val getAllFoodDescriptions: LiveData<List<FoodDescription>> = foodDescriptionDao.getAllFoodDescriptions()

    suspend fun addFoodDescription(foodDescription: FoodDescription) {
        foodDescriptionDao.addFoodDescription(foodDescription)
    }

}