package com.office14.coffeedose.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("select * from users")
    fun getAllUsers(): LiveData<List<UserDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(vararg users: UserDbo)

    @Query("delete from users")
    fun clear()

    @Query("select * from users where email = :email ")
    fun getByEmail(email : String) : LiveData<List<UserDbo>>
}