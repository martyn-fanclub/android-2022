package com.old.leopards.restaurant.database.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.old.leopards.restaurant.database.RestaurantDatabase
import com.old.leopards.restaurant.database.entities.Language
import com.old.leopards.restaurant.database.repositories.LanguageRepository
import kotlinx.coroutines.Job
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

    fun addAllLanguages(vararg languages: Language): List<Long> {
        return repository.addAllLanguages(*languages)
    }

    fun addLanguage(language: Language): Job {
        return viewModelScope.launch {
            repository.addLanguage(language)
        }
    }

}
