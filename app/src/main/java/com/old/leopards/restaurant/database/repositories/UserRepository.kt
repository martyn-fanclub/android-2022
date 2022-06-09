package com.old.leopards.restaurant.database.repositories

import com.old.leopards.restaurant.database.dao.UserDao
import com.old.leopards.restaurant.database.entities.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val getAllUsers: Flow<List<User>> = userDao.getAllUsers()

    suspend fun createUser(user: User) {
        userDao.createUser(user)
    }

    fun getUser(name: String): User? {
        return userDao.getUser(name)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}
