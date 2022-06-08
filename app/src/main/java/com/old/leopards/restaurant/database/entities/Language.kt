package com.old.leopards.restaurant.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class Language(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)
