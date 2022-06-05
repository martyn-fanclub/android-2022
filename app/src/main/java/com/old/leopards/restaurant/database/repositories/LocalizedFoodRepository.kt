package com.old.leopards.restaurant.database.repositories

import androidx.lifecycle.LiveData
import com.old.leopards.restaurant.database.dao.LocalizedFoodDao
import com.old.leopards.restaurant.database.entities.LocalizedFood
import kotlinx.coroutines.flow.Flow

class LocalizedFoodRepository(private val localizedFoodDao: LocalizedFoodDao, language: Int) {

    val getLocalizedFoodsByLanguage: Flow<List<LocalizedFood>> =
        localizedFoodDao.getAllFoodItemsLocalizedByLanguageId(language)

}