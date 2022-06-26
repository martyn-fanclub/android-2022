package com.old.leopards.restaurant.database.repositories

import com.old.leopards.restaurant.database.dao.UserDao
import com.old.leopards.restaurant.database.entities.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val getAllUsers: Flow<List<User>> = userDao.getAllUsers()

    fun createUser(user: User): Long {
        return userDao.createUser(user)
    }

    fun getUserByName(name: String): User? {
        return userDao.getUserByName(name)
    }

    fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}
