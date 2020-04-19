package com.office14.coffeedose.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.office14.coffeedose.database.OrderDao
import com.office14.coffeedose.database.OrderDbo
import com.office14.coffeedose.database.OrdersQueueDao
import com.office14.coffeedose.domain.Order
import com.office14.coffeedose.domain.OrderDetailFull
import com.office14.coffeedose.domain.OrderStatus
import com.office14.coffeedose.network.CoffeeApiService
import com.office14.coffeedose.network.CreateOrderBody
import com.office14.coffeedose.network.HttpExceptionEx
import com.office14.coffeedose.repository.PreferencesRepository.EMPTY_STRING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrdersRepository @Inject constructor(private  val ordersDao : OrderDao, private val ordersQueueDao : OrdersQueueDao, private val coffeeApi : CoffeeApiService) {

    fun getCurrentQueueOrderNormal() : Order? {
        val list = ordersQueueDao.getAllNormal()
        if (list.isNullOrEmpty()) return null
        return list[0].toDomainModel()
    }

    fun getOrderById(orderId:Int) = Transformations.map(ordersDao.getById(orderId)){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    fun getCurrentQueueOrder() : LiveData<Order> = Transformations.map(ordersQueueDao.getAll()){ itDbo ->
        if (itDbo.size == 1){
            return@map itDbo[0].toDomainModel()
        }
        return@map null
    }

    fun getCurrentQueueOrderByUser(email:LiveData<String>) : LiveData<Order> = Transformations.map(email){

        var list = if (it == EMPTY_STRING) ordersQueueDao.getAll() else  ordersQueueDao.getAllForUser(it)

        val item = list.value?.get(0)
        if (list?.value?.size == 1){
            return@map item!!.toDomainModel()
        }
        return@map null

        /*if (itDbo.size == 1){
            return@map itDbo[0].toDomainModel()
        }
        return@map null*/
    }

    /*fun getCurrentQueueOrderByUser(email:String) : LiveData<Order> = Transformations.map(ordersQueueDao.getAllForUser(email)){ itDbo ->
        if (itDbo.size == 1){
            return@map itDbo[0].toDomainModel()
        }
        return@map null
    }*/

    fun queueOrderStatus(email:LiveData<String>) : LiveData<OrderStatus> = Transformations.map( email ){

        var list = if (it == EMPTY_STRING) ordersQueueDao.getAll() else  ordersQueueDao.getAllForUser(it)

        val item = list.value?.get(0)
        if (list?.value?.size == 1){
            return@map OrderStatus.getStatusByString(item!!.statusCode)
        }
        return@map OrderStatus.NONE
    }

    private fun composeAuthHeader(token:String?) = "Bearer $token"

    suspend fun createOrder(orders:List<OrderDetailFull>,token:String?) : Int {
        var id = -1
        val ordersBody = CreateOrderBody()
        ordersBody.fillWithOrders(orders)

        withContext(Dispatchers.IO) {
            var result = coffeeApi.createOrderAsync(ordersBody,composeAuthHeader(token)).await()
            if (result.hasError())
                throw HttpExceptionEx(result.getError())
            else {
                ordersQueueDao.insertAllOrders(result.payload!!.toOrderQueueDataBaseModel())
                ordersDao.insertAllOrders(result.payload!!.toDataBaseModel())
                id = result.payload!!.id
            }

        }
        return id
    }

    suspend fun refreshOrder(orderId:Int, token:String?) {
        try {
            withContext(Dispatchers.IO) {

                val savedOrder = ordersQueueDao.getById(orderId).value?.get(0)

                var result = coffeeApi.getOrderByIdAsync(orderId,composeAuthHeader(token)).await()
                if (result.hasError())
                    throw HttpExceptionEx(result.getError())
                else {

                    val remoteOrder = result.payload!!.toDataBaseModel().toDomainModel()
                    if (savedOrder != null && savedOrder?.toDomainModel().statusCode != remoteOrder.statusCode && remoteOrder.statusName?.toLowerCase() == "ready") {
                        ordersQueueDao.insertAllOrders(result.payload!!.toOrderQueueDataBaseModel())
                        ordersDao.insertAllOrders(result.payload!!.toDataBaseModel())
                    }
                }
            }
            delay(5000)
        }
        catch (ex:Exception){
            Log.d("OrdersRepository.refreshOrder", ex.message?:"")
        }
    }

    suspend fun clearQueueOrder(orderId:Int) {
        try {
            withContext(Dispatchers.IO) {
                val savedOrder = ordersQueueDao.getById(orderId).value?.get(0)
                savedOrder?.let { ordersQueueDao.delete(it) }
            }

        }
        catch (ex:Exception){
            Log.d("OrdersRepository.clearQueueOrder", ex.message?:"")
        }
    }

}