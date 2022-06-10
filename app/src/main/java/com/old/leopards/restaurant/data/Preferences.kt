package com.old.leopards.restaurant.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.old.leopards.restaurant.database.entities.User
import com.old.leopards.restaurant.ui.Global
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Preferences(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "setting")
        private val firstLaunch = booleanPreferencesKey("isFirstLaunch")
        private val initUser: User = User(0, "", "", "", "")
        private var currentUser: User = initUser
        var userAddress = ""
    }

    val isFirstLaunch: Flow<Boolean>
        get() = context.dataStore.data.map {
            it[firstLaunch] ?: true
        }

    suspend fun setFirstLaunch(value: Boolean) {
        context.dataStore.edit { it[firstLaunch] = value }
    }

    fun isNotFirstAuthorizedLaunch(): Boolean {
        return currentUser != initUser
    }

    fun getCurrentUser(): User {
        return currentUser
    }

    fun setCurrentUser(user: User) {
        currentUser = user
    }

    fun setUserAddress(address: String) {
        userAddress = address
    }

    fun getUserAddress(): String {
        return userAddress
    }

    fun resetUser() {
        currentUser = initUser
        userAddress = ""
    }
}
