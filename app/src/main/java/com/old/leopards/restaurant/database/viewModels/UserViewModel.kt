package com.old.leopards.restaurant.database.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.old.leopards.restaurant.database.RestaurantDatabase
import com.old.leopards.restaurant.database.entities.User
import com.old.leopards.restaurant.database.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val getAllUsers: Flow<List<User>>
    var getUserByName: User? = null
    var getUserByEmail: User? = null
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

    fun getUserByName(name: String): User? {
        viewModelScope.launch(Dispatchers.IO) {
            getUserByName = repository.getUserByName(name).first()
        }
        return getUserByName
    }

    fun getUserByEmail(email: String): User? {
        viewModelScope.launch(Dispatchers.IO) {
            getUserByEmail = repository.getUserByEmail(email).first()
        }
        return getUserByEmail
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }
}
