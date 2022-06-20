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

    fun createUser(user: User): Long {
        return repository.createUser(user)
    }

    fun getUserByName(name: String): User? {
        return repository.getUserByName(name)
    }

    fun getUserByEmail(email: String): User? {
        return repository.getUserByEmail(email)
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }
}
