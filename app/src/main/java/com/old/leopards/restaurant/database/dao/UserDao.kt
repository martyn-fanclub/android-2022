package com.old.leopards.restaurant.database.dao

import androidx.room.*
import com.old.leopards.restaurant.database.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createUser(user: User)

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE login = :name")
    fun getUser(name: String): User?

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateUser(user: User)
}
