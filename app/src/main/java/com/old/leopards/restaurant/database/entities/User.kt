package com.old.leopards.restaurant.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val login: String,
    val password: String,
    val name: String,
    val mailbox: String,
    val photoLink: String
)
