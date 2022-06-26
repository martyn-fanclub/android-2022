package com.old.leopards.restaurant.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var login: String,
    var password: String,
    var email: String,
    var photoLink: String?,
)
