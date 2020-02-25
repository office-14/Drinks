package com.coffeedose.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OrderDao{

    @Query("select * from orders")
    fun getAll() : LiveData<List<OrderDbo>>

    @Query("select * from orders where id = :orderId")
    fun getById(orderId:Int) : LiveData<List<OrderDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOrders(vararg orders: OrderDbo)

    @Delete
    fun delete(order:OrderDbo)

    @Query("delete from orders")
    fun clear()
}