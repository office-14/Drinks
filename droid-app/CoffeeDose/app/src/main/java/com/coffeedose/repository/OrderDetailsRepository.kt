package com.coffeedose.repository

import android.util.Log
import androidx.lifecycle.Transformations
import com.coffeedose.database.OrderDetailDao
import com.coffeedose.database.OrderDetailDbo
import com.coffeedose.database.OrderDetailsContaner
import com.coffeedose.domain.OrderDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderDetailsRepository(private val orderDetailsDao : OrderDetailDao) {

    val unAttachedOrderDetails = Transformations.map(orderDetailsDao.getUnAttachedDetails()){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    fun getOrderDetailsByOrderId(orderId:Int) = Transformations.map(orderDetailsDao.getDetailsByOrderId(orderId)){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    suspend fun delete(oderDetails : OrderDetail){
        try {
            withContext(Dispatchers.IO) {
                orderDetailsDao.delete(OrderDetailDbo(oderDetails))
            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.deleteById", ex.message)
        }
    }

    suspend fun deleteUnAttached(){
        try {
            withContext(Dispatchers.IO) {
                orderDetailsDao.deleteUnAttached()
            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.deleteUnAttached", ex.message)
        }
    }

    suspend fun insertAll(orderDetails:List<OrderDetail>){
        try {
            withContext(Dispatchers.IO) {
                orderDetailsDao.insertOrderDetailsAndAddIns(*orderDetails.map {  OrderDetailsContaner(it)}.toTypedArray())
            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.insertAll", ex.message)
        }
    }

    suspend fun insertNew(orderDetail:OrderDetail){
        try {
            withContext(Dispatchers.IO) {
                orderDetailsDao.insertOrderDetailsAndAddIns(OrderDetailsContaner(orderDetail))
            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.insertAll", ex.message)
        }
    }
}