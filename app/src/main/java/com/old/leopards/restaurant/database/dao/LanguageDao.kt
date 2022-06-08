package com.old.leopards.restaurant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.old.leopards.restaurant.database.entities.Language
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLanguage(language: Language)

    @Query("SELECT * FROM languages ORDER BY id ASC")
    fun getAllLanguages(): Flow<List<Language>>
}
