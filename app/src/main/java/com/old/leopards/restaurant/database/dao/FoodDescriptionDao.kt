package com.old.leopards.restaurant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.old.leopards.restaurant.database.entities.FoodDescription

@Dao
interface FoodDescriptionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFoodDescription(foodDescription: FoodDescription)

    @Query("SELECT * FROM food_descriptions ORDER BY id ASC")
    fun getAllFoodDescriptions(): LiveData<List<FoodDescription>>
}