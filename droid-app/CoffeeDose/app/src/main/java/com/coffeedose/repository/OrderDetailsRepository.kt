package com.coffeedose.repository

import android.util.Log
import androidx.lifecycle.Transformations
import com.coffeedose.database.OrderDetailDao
import com.coffeedose.database.OrderDetailDbo
import com.coffeedose.domain.OrderDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderDetailsRepository(private val orderDetailsDao : OrderDetailDao) {

    val unAttachedOrderDetails = Transformations.map(orderDetailsDao.getUnAttachedDetails()){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    fun getOrderDetailsByorderId(orderId:Int) = Transformations.map(orderDetailsDao.getDetailsByOrderId(orderId)){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    suspend fun insertAll(orderDetails:List<OrderDetail>){
        try {
            withContext(Dispatchers.IO) {
                orderDetailsDao.insertAllOrderDetails(*orderDetails.map { OrderDetailDbo(it) }.toTypedArray())
            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.insertAll", ex.message)
        }
    }

}