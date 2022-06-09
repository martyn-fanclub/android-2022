package com.old.leopards.restaurant.database.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.old.leopards.restaurant.database.RestaurantDatabase
import com.old.leopards.restaurant.database.entities.FoodItem
import com.old.leopards.restaurant.database.repositories.FoodItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FoodItemViewModel(application: Application) : AndroidViewModel(application) {

    val getAllFoodItems: Flow<List<FoodItem>>
    private val repository: FoodItemRepository

    init {
        val foodItemDao = RestaurantDatabase.getDatabase(application).foodItemDao()
        repository = FoodItemRepository(foodItemDao)
        getAllFoodItems = repository.getAllFoodItems
    }

    fun addFoodItem(foodItem: FoodItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFoodItem(foodItem)
        }
    }

}
