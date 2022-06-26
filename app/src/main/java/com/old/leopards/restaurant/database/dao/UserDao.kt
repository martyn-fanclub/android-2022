package com.old.leopards.restaurant.database.dao

import androidx.room.*
import com.old.leopards.restaurant.database.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    fun createUser(user: User): Long

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE login = :name")
    fun getUserByName(name: String): User?

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): User?

    @Update
    suspend fun updateUser(user: User)
}
