package com.office14.coffeedose.repository

import android.util.Log
import androidx.lifecycle.Transformations
import com.office14.coffeedose.database.OrderDao
import com.office14.coffeedose.database.OrderDetailDao
import com.office14.coffeedose.domain.OrderDetailFull
import com.office14.coffeedose.network.CoffeeApi
import com.office14.coffeedose.network.CreateOrderBody
import com.office14.coffeedose.network.HttpExceptionEx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class OrdersRepository(private  val ordersDao : OrderDao, private val orderDetailsDao : OrderDetailDao) {

    fun getOrderById(orderId:Int) = Transformations.map(ordersDao.getById(orderId)){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    private fun composeAuthHeader(token:String?) = "Bearer $token"

    suspend fun createOrder(orders:List<OrderDetailFull>,token:String?) : Int {

        /* try {*/
        var id = -1
        val ordersBody = CreateOrderBody()
        ordersBody.fillWithOrders(orders)

        withContext(Dispatchers.IO) {
            var result = CoffeeApi.retrofitService.createOrderAsync(ordersBody,composeAuthHeader(token)).await()
            if (result.hasError())
                throw HttpExceptionEx(result.getError())
            else {
                ordersDao.insertAllOrders(result.payload!!.toDataBaseModel())
                id = result.payload!!.id
                PreferencesRepository.saveLastOrderId(id)
            }

        }
        return id


        /*}
        catch (ex:Exception){
            Log.d("OrdersRepository.createOrder", ex.message?:"")
            return -1
        }*/
    }

    suspend fun refreshOrder(orderId:Int, token:String?) {
        try {

            withContext(Dispatchers.IO) {
                var result = CoffeeApi.retrofitService.getOrderByIdAsync(orderId,composeAuthHeader(token)).await()
                if (result.hasError())
                    throw HttpExceptionEx(result.getError())
                else
                    ordersDao.insertAllOrders(result.payload!!.toDataBaseModel())
            }

            delay(5000)
        }
        catch (ex:Exception){
            Log.d("OrdersRepository.refreshOrder", ex.message?:"")
        }
    }

}