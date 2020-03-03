package com.office14.coffeedose.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CoffeeDao {

    /* Drinks  */
    @Query("select * from drinks")
    fun getAllDrinks(): LiveData<List<CoffeeDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDrinks(vararg drinks: CoffeeDbo)

    @Query("delete from drinks")
    fun clear()

    @Query("delete from drinks where id not in (:idsList)")
    fun clearByNotInList(idsList:List<Int>)

    @Transaction
    fun refreshDrinks(drinks : List<CoffeeDbo>){
        insertAllDrinks(*drinks.toTypedArray())
        clearByNotInList(drinks.map { it.id })
    }
}