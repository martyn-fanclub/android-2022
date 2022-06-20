package com.old.leopards.restaurant.database.repositories

import com.old.leopards.restaurant.database.dao.OrderDao
import com.old.leopards.restaurant.database.entities.Order
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val orderDao: OrderDao, user: Int) {

    val getAllOrdersByUser: Flow<List<Order>> = orderDao.getAllOrdersByUserId(user)

    suspend fun addOrder(order: Order) {
        orderDao.addOrder(order)
    }

    fun addAllOrders(vararg order: Order): List<Long> {
        return orderDao.addAllOrders(*order)
    }

}
