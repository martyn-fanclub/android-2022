package com.old.leopards.restaurant.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "food_descriptions", foreignKeys = [
        ForeignKey(
            entity = FoodItem::class,
            parentColumns = ["id"],
            childColumns = ["food_item_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Language::class,
            parentColumns = ["id"],
            childColumns = ["language_id"],
            onDelete = CASCADE
        )
    ]
)
data class FoodDescription(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "language_id")
    val languageId: Int,
    @ColumnInfo(name = "food_item_id")
    val foodItemId: Int,
    val name: String,
    val description: String
)
