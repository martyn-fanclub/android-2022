package com.old.leopards.restaurant.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Preferences(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "setting")
        private val firstLaunch = booleanPreferencesKey("isFirstLaunch")
    }


    val isFirstLaunch: Flow<Boolean>
        get() = context.dataStore.data.map {
            it[firstLaunch] ?: true
        }

    suspend fun setFirstLaunch(value: Boolean) {
        context.dataStore.edit { it[firstLaunch] = value }
    }
}