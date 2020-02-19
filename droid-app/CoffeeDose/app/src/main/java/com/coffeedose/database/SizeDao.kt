package com.coffeedose.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SizeDao {

    @Query("select * from drink_size where drink_id = :id")
    fun getSizesByDrinkId(id:Int) : LiveData<List<SizeDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSizes(vararg sizes: SizeDbo)

    @Query("delete from drink_size")
    fun clear()

    @Query("delete from drink_size where drink_id = :id")
    fun deleteByDrinkId(id:Int)
}