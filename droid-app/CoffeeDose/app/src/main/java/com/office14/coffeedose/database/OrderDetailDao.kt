package com.office14.coffeedose.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OrderDetailDao {

    @Transaction
    @Query("select * from order_details where order_id is null")
    fun getUnAttachedDetails(): LiveData<List<OrderDetailAndDrinkAndSize>>

    @Transaction
    @Query("select * from order_details where order_id = :orderId")
    fun getDetailsByOrderId(orderId:Int): LiveData<List<OrderDetailAndDrinkAndSize>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOrderDetails(vararg orderDetails: OrderDetailDbo)  : List<Long>

    @Query("delete from order_details where order_id is null")
    fun deleteUnAttached()

    //@Update
    //fun updateUnAttachedWithOrderId(orderDetails:List<Order>)

    /*@Query("delete from order_details where order_id = :orderId")
    fun deleteByOrderId(orderId:Int)*/

    @Delete
    fun delete(orderDetails : OrderDetailDbo)

    @Query("delete from order_details")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOrderDetailsToAddInsCross(vararg crossRefs: OrderDetailsAndAddinsCrossRef)

    @Transaction
    fun insertOrderDetailsAndAddIns(vararg orderDetails: OrderDetailsContaner){
        val newIds = insertAllOrderDetails(*orderDetails.map { it.orderDetails }.toTypedArray())

        val crossRefsList : MutableList<OrderDetailsAndAddinsCrossRef> = mutableListOf()

        for ((index, odId) in newIds.withIndex()){

            orderDetails[index].addIns.forEach {
                crossRefsList.add( OrderDetailsAndAddinsCrossRef(odId.toInt(),it.id) )
            }
        }

        insertAllOrderDetailsToAddInsCross(*crossRefsList.toTypedArray())
    }
}