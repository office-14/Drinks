package com.coffeedose.repository

import android.util.Log
import androidx.lifecycle.Transformations
import com.coffeedose.database.OrderDao
import com.coffeedose.database.OrderDetailDao
import com.coffeedose.database.OrderDetailDbo
import com.coffeedose.domain.OrderDetailFull
import com.coffeedose.network.CoffeeApi
import com.coffeedose.network.CreateOrderBody
import com.coffeedose.network.HttpExceptionEx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class OrdersRepository(private  val ordersDao : OrderDao,private val orderDetailsDao : OrderDetailDao) {

    fun getOrderById(orderId:Int) = Transformations.map(ordersDao.getById(orderId)){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }


    suspend fun createOrder(orders:List<OrderDetailFull>) : Int {
       /* try {*/
            var id = -1
            val ordersBody = CreateOrderBody()
            ordersBody.fillWithOrders(orders)

            withContext(Dispatchers.IO) {
                var result = CoffeeApi.retrofitService.createOrder(ordersBody).await()
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

    suspend fun refreshOrder(orderId:Int) {
        try {

            withContext(Dispatchers.IO) {
                var result = CoffeeApi.retrofitService.getOrderById(orderId).await()
                if (result.hasError())
                    throw HttpExceptionEx(result.getError())
                else
                    ordersDao.insertAllOrders(result.payload!!.toDataBaseModel())
            }

            delay(5000)
        }
        catch (ex:Exception){
            Log.d("OrdersRepository.createOrderAndSaveId", ex.message?:"")
        }
    }

}