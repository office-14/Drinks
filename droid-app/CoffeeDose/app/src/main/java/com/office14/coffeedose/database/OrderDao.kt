package com.office14.coffeedose.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OrderDao{

    @Query("select * from orders")
    fun getAll() : LiveData<List<OrderDbo>>

    @Query("select * from orders where not finished")
    fun getAllNotFinished() : LiveData<List<OrderDbo>>

    @Query("select * from orders where owner = :email")
    fun getAllForUser(email:String) : LiveData<List<OrderDbo>>

    @Query("select * from orders where id = :orderId")
    fun getById(orderId:Int) : LiveData<List<OrderDbo>>

    @Query("select * from orders where id = :orderId and owner = :email")
    fun getByIdAndOwner(orderId:Int, email:String) : LiveData<List<OrderDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOrders(vararg orders: OrderDbo)

    @Delete
    fun delete(order:OrderDbo)

    @Query("delete from orders where owner = :email")
    fun deleteByUser(email: String)

    @Query("update orders set finished = 1 where owner = :email")
    fun markAsFinishedForUser(email: String)

    @Query("delete from orders")
    fun clear()
}