package com.old.leopards.restaurant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.old.leopards.restaurant.database.entities.LocalizedFood

@Dao
interface LocalizedFoodDao {

    @Query(
        "SELECT food_items.id, name, description, portionSize, price, photoLink " +
        "FROM food_descriptions " +
        "INNER JOIN food_items ON food_item_id = food_items.id " +
        "WHERE language_id = :id"
    )
    fun getAllFoodItemsLocalizedByLanguageId(id : Int): LiveData<List<LocalizedFood>>

}