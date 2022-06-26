package com.old.leopards.restaurant.database.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.old.leopards.restaurant.database.RestaurantDatabase
import com.old.leopards.restaurant.database.entities.Order
import com.old.leopards.restaurant.database.repositories.OrderRepository
import com.old.leopards.restaurant.ui.Global
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    val getAllOrdersByUserId: Flow<List<Order>>
    private val repository: OrderRepository

    init {
        val orderDao = RestaurantDatabase.getDatabase(application).orderDao()
        repository = OrderRepository(orderDao, Global.currentUser.id.toInt())
        getAllOrdersByUserId = repository.getAllOrdersByUser
    }

    fun addAllOrders(vararg orders: Order): List<Long> {
        return repository.addAllOrders(*orders)
    }

    fun addOrder(order: Order): Job {
        return viewModelScope.launch {
            repository.addOrder(order)
        }
    }

}
