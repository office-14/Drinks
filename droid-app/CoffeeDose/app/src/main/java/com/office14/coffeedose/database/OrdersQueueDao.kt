package com.office14.coffeedose.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OrdersQueueDao{

    @Query("select * from orders_queue")
    fun getAllNormal() : List<OrderQueueDbo>

    @Query("select * from orders_queue")
    fun getAll() : LiveData<List<OrderQueueDbo>>

    @Query("select * from orders_queue where id = :orderId")
    fun getById(orderId:Int) : LiveData<List<OrderQueueDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOrders(vararg orders: OrderQueueDbo)

    @Delete
    fun delete(order:OrderQueueDbo)

    @Query("delete from orders_queue")
    fun clear()
}