package com.old.leopards.restaurant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.old.leopards.restaurant.database.entities.FoodItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFoodItem(foodItem: FoodItem)

    @Query("SELECT * FROM food_items ORDER BY id ASC")
    fun getAllFoodItems(): Flow<List<FoodItem>>
}