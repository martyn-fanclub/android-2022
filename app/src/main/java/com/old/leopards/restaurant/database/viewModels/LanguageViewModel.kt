package com.old.leopards.restaurant.database.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.old.leopards.restaurant.database.RestaurantDatabase
import com.old.leopards.restaurant.database.entities.Language
import com.old.leopards.restaurant.database.repositories.LanguageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LanguageViewModel(application: Application) : AndroidViewModel(application) {

    val getAllLanguages: Flow<List<Language>>
    private val repository: LanguageRepository

    init {
        val languageDao = RestaurantDatabase.getDatabase(application).languageDao()
        repository = LanguageRepository(languageDao)
        getAllLanguages = repository.getAllLanguages
    }

    fun addLanguage(language: Language) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLanguage(language)
        }
    }

}