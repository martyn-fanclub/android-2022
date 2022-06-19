package com.old.leopards.restaurant.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.old.leopards.restaurant.database.dao.*
import com.old.leopards.restaurant.database.entities.*

@Database(
    entities = [User::class, FoodDescription::class,
        FoodItem::class, Language::class, Order::class], version = 1, exportSchema = true
)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun foodItemDao(): FoodItemDao
    abstract fun foodDescriptionDao(): FoodDescriptionDao
    abstract fun languageDao(): LanguageDao
    abstract fun localizedFoodDao(): LocalizedFoodDao
    abstract fun orderDao(): OrderDao

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
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
