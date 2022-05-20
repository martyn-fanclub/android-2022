package com.old.leopards.restaurant.database.repositories

import androidx.lifecycle.LiveData
import com.old.leopards.restaurant.database.dao.LocalizedFoodDao
import com.old.leopards.restaurant.database.entities.LocalizedFood

class LocalizedFoodRepository(private val localizedFoodDao: LocalizedFoodDao, language: Int) {

    val getLocalizedFoodsByLanguage: LiveData<List<LocalizedFood>> =
        localizedFoodDao.getAllFoodItemsLocalizedByLanguageId(language)

}