package com.coffeedose.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AddinDao {
    @Query("select * from add_ins")
    fun getAllAddins(): LiveData<List<AddinDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAddins(vararg videos: AddinDbo)

    @Query("delete from add_ins")
    fun clear()
}