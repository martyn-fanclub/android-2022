package com.old.leopards.restaurant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.old.leopards.restaurant.database.entities.FoodDescription
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDescriptionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFoodDescription(foodDescription: FoodDescription): Long

    @Query("SELECT * FROM food_descriptions ORDER BY id ASC")
    fun getAllFoodDescriptions(): Flow<List<FoodDescription>>
}
