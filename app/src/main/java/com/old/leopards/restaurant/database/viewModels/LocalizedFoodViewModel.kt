package com.old.leopards.restaurant.database.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.old.leopards.restaurant.database.RestaurantDatabase
import com.old.leopards.restaurant.database.entities.LocalizedFood
import com.old.leopards.restaurant.database.repositories.LocalizedFoodRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

class LocalizedFoodViewModel(application: Application) : AndroidViewModel(application) {

    val getLocalizedFoodByLanguageId: Flow<List<LocalizedFood>>
    private val repository: LocalizedFoodRepository

    init {
        val localizedFoodDao = RestaurantDatabase.getDatabase(application).localizedFoodDao()
        val lang: Int
        if (Locale.getDefault().language.equals("ru")) {
            lang = 0
        } else {
            lang = 1
        }
        repository = LocalizedFoodRepository(localizedFoodDao, lang)
        getLocalizedFoodByLanguageId = repository.getLocalizedFoodsByLanguage
    }

}