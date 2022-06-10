package com.old.leopards.restaurant.database.repositories

import com.old.leopards.restaurant.database.dao.LanguageDao
import com.old.leopards.restaurant.database.entities.Language
import kotlinx.coroutines.flow.Flow

class LanguageRepository(private val languageDao: LanguageDao) {

    val getAllLanguages: Flow<List<Language>> = languageDao.getAllLanguages()

    suspend fun addLanguage(language: Language) {
        languageDao.addLanguage(language)
    }

    fun addAllLanguages(vararg language: Language): List<Long> {
        return languageDao.addAllLanguages(*language)
    }

}
