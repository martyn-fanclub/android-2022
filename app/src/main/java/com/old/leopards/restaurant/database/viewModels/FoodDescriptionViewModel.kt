package com.old.leopards.restaurant.database.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.old.leopards.restaurant.database.RestaurantDatabase
import com.old.leopards.restaurant.database.entities.FoodDescription
import com.old.leopards.restaurant.database.repositories.FoodDescriptionRepository
import kotlinx.coroutines.flow.Flow

class FoodDescriptionViewModel(application: Application) : AndroidViewModel(application) {

    val getAllFoodDescriptions: Flow<List<FoodDescription>>
    private val repository: FoodDescriptionRepository

    init {
        val foodDescriptionDao = RestaurantDatabase.getDatabase(application).foodDescriptionDao()
        repository = FoodDescriptionRepository(foodDescriptionDao)
        getAllFoodDescriptions = repository.getAllFoodDescriptions
    }

    fun addFoodDescription(foodDescription: FoodDescription): Long {
        return repository.addFoodDescription(foodDescription)
    }

}
