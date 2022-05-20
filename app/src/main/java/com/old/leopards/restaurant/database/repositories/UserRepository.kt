package com.old.leopards.restaurant.database.repositories

import androidx.lifecycle.LiveData
import com.old.leopards.restaurant.database.dao.UserDao
import com.old.leopards.restaurant.database.entities.User

class UserRepository(private val userDao: UserDao) {

    val getAllUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

}