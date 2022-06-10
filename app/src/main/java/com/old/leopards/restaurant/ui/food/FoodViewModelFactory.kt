package com.old.leopards.restaurant.ui.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.old.leopards.restaurant.database.viewModels.LocalizedFoodViewModel

class FoodViewModelFactory(private val localizedFoodViewModel: LocalizedFoodViewModel) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            return FoodViewModel(localizedFoodViewModel) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}