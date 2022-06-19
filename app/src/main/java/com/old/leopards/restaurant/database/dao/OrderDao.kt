package com.old.leopards.restaurant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.old.leopards.restaurant.database.entities.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addOrder(order: Order)

    @Insert
    fun addAllOrders(vararg orders: Order): List<Long>

    @Query("SELECT * FROM orders WHERE user_id = :id ORDER BY id ASC")
    fun getAllOrdersByUserId(id: Int): Flow<List<Order>>
}
