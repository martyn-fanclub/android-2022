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
        var userId = PreferencesKey<String>("getUserId")
        var userLogin = PreferencesKey<String>("getUserLogin")
        var userPassowrd = PreferencesKey<String>("getUserPassword")
        var userEmail = PreferencesKey<String>("getUserEmail")
        var userPhotoLink = PreferencesKey<String>("getUserPhotoLink")
        var userAddress = PreferencesKey<String>("getUserAddress")
    }

    val isFirstLaunch: Flow<Boolean>
        get() = context.dataStore.data.map {
            it[firstLaunch] ?: true
        }

    suspend fun setFirstLaunch(value: Boolean) {
        context.dataStore.edit { it[firstLaunch] = value }
    }

    val isNotFirstAuthorizedLaunch: Flow<Boolean>
        get() =  context.dataStore.data.map {
            it[userLogin] != ""
        }

    val getCurrentUser: Flow<User>
        get() = context.dataStore.data.map {
            User(it[userId], it[userLogin], it[userPassowrd], it[userEmail], it[userPhotoLink])
        }

    fun setCurrentUser(user: User) {
        context.dataStore.edit {
            it[userId] = user.id
            it[userLogin] = user.login
            it[userPassowrd] = user.password
            it[userEmail] = user.email
            it[userPhotoLink] = user.photoLink
        }
    }

    fun setUserAddress(address: String) {
        context.dataStore.edit { it[userAddress] = address }
    }

    val getUserAddress: Flow<String>
        get() = context.dataStore.data.map {
            it[userAddress]
        }

    fun resetUser() {
        context.dataStore.edit {
            it[userId] = 0
            it[userLogin] = ""
            it[userPassowrd] = ""
            it[userEmail] = ""
            it[userPhotoLink] = ""
            it[userAddress] = ""
        }
    }
}
