package com.coffeedose.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderDetailDao {
    @Query("select * from order_details where order_id is null")
    fun getUnAttachedDetails(): LiveData<List<OrderDetailDbo>>

    @Query("select * from order_details where order_id = :orderId")
    fun getDetailsByOrderId(orderId:Int): LiveData<List<OrderDetailDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOrderDetails(vararg videos: OrderDetailDbo)

    @Query("delete from order_details where order_id is null")
    fun deleteUnAttached()

    @Query("delete from order_details where order_id = :orderId")
    fun deleteByOrderId(orderId:Int)

    @Query("delete from order_details")
    fun clear()
}