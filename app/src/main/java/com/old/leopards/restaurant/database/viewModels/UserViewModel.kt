package com.old.leopards.restaurant.database.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.old.leopards.restaurant.database.RestaurantDatabase
import com.old.leopards.restaurant.database.entities.User
import com.old.leopards.restaurant.database.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val getAllUsers: Flow<List<User>>
    private val repository: UserRepository

    init {
        val userDao = RestaurantDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        getAllUsers = repository.getAllUsers
    }

    fun createUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createUser(user)
        }
    }

    fun getUser(name: String): User {
        return repository.getUser(name)
    }

    fun updateUser(user: User) {
        repository.updateUser(user)
    }
}
