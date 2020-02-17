package com.coffeedose.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CoffeeDao {

    /* Drinks  */
    @Query("select * from drinks")
    fun getAllDrinks(): LiveData<List<CoffeeDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDrinks(vararg videos: CoffeeDbo)

    /* Doses */
}