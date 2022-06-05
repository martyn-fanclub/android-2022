package com.old.leopards.restaurant.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import com.old.leopards.restaurant.database.dao.*
import com.old.leopards.restaurant.database.entities.FoodDescription
import com.old.leopards.restaurant.database.entities.FoodItem
import com.old.leopards.restaurant.database.entities.Language
import com.old.leopards.restaurant.database.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

@Database(
    entities = [User::class, FoodDescription::class,
        FoodItem::class, Language::class], version = 1, exportSchema = true
)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun foodItemDao(): FoodItemDao
    abstract fun foodDescriptionDao(): FoodDescriptionDao
    abstract fun languageDao(): LanguageDao
    abstract fun localizedFoodDao(): LocalizedFoodDao

    companion object {
        @Volatile
        private var INSTANCE: RestaurantDatabase? = null

        fun getDatabase(context: Context): RestaurantDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDatabase::class.java,
                    "restaurant_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
