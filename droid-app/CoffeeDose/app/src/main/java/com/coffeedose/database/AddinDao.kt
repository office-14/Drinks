package com.coffeedose.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AddinDao {
    @Query("select * from add_ins")
    fun getAllAddins(): LiveData<List<AddinDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAddins(vararg videos: AddinDbo)

    @Query("delete from add_ins")
    fun clear()

    @Query("delete from add_ins where id not in (:idsList)")
    fun clearByNotInList(idsList:List<Int>)

    @Transaction
    fun refreshAddins(addins:List<AddinDbo>){
        insertAllAddins(*addins.toTypedArray())
        clearByNotInList(addins.map { it.id })
    }
}