package com.old.leopards.restaurant.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val login: String,
    val password: String,
    val email: String,
    val photoLink: String?,
)
