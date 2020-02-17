package com.coffeedose.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CoffeeDbo::class], version = 1, exportSchema = false)
abstract class CoffeeDatabase : RoomDatabase() {

    abstract val sleepDatabaseDao: CoffeeDao

    companion object {

        @Volatile
        private var INSTANCE: CoffeeDatabase? = null

        fun getInstance(context: Context): CoffeeDatabase {

            synchronized(this) {

                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CoffeeDatabase::class.java,
                        "drinks_database"
                    ).fallbackToDestructiveMigration()
                     .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}