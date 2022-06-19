package com.old.leopards.restaurant.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "orders", foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
])
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val price: Int,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val orderDate: String,
    @ColumnInfo(name = "user_id")
    val userId: Int
)
