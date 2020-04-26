package com.office14.coffeedose.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.office14.coffeedose.database.OrderDao
import com.office14.coffeedose.domain.Order
import com.office14.coffeedose.domain.OrderDetailFull
import com.office14.coffeedose.domain.OrderStatus
import com.office14.coffeedose.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrdersRepository @Inject constructor(private  val ordersDao : OrderDao, private val coffeeApi : CoffeeApiService) {

    private val allOrders = ordersDao.getAll()

    fun getCurrentQueueOrderNormal() : Order? {
        val list = ordersDao.getAll().value
        if (list.isNullOrEmpty()) return null
        return list[0].toDomainModel()
    }

    fun getOrderById(orderId:Int) = Transformations.map(ordersDao.getById(orderId)){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    fun getCurrentQueueOrder() : LiveData<Order> = Transformations.map(ordersDao.getAll()){ itDbo ->
        if (itDbo.size == 1){
            return@map itDbo[0].toDomainModel()
        }
        return@map null
    }

    fun getCurrentQueueOrderByUser(email:LiveData<String>) : LiveData<Order> {

        val result = MediatorLiveData<Order>()
        val allOrders = ordersDao.getAllNotFinished()

        val update =  {
            val list = allOrders?.value?.filter { it.owner == email.value!! } ?: listOf()
            result.value = if (list.size == 1) list[0].toDomainModel() else null
        }

        result.addSource(email){update.invoke()}
        result.addSource(allOrders){update.invoke()}

        return result
    }

    suspend fun getCurrentNotFinishedOrderByUser(email: String) : Order? {

        var result : Order? = null

        withContext(Dispatchers.IO) {
            val list = ordersDao.getAllForUserNotFinishedStraight(email)

            if (list.size == 1){
                result =  list[0].toDomainModel()
            }

        }
        return result
    }


    fun queueOrderStatus(email:LiveData<String>) : LiveData<OrderStatus> {

        val result = MediatorLiveData<OrderStatus>()
        val allOrdersInQueue = ordersDao.getAllNotFinished()

        val update = {
            val list = allOrdersInQueue.value?.filter { it.owner == email.value }
            if (list?.size == 1){
                result.value = OrderStatus.getStatusByString(list[0].statusCode)
            }
            else
                result.value = OrderStatus.NONE
        }


        result.addSource(email){update.invoke()}
        result.addSource(allOrdersInQueue){update.invoke()}

        return result

    }

    private fun composeAuthHeader(token:String?) = "Bearer $token"

    suspend fun createOrder(orders:List<OrderDetailFull>, comment:String?, token:String?, email: String) : Int {
        var id = -1
        val ordersBody = CreateOrderBody(comment)
        ordersBody.fillWithOrders(orders)

        withContext(Dispatchers.IO) {
            var result = coffeeApi.createOrderAsync(ordersBody,composeAuthHeader(token)).await()
            if (result.hasError())
                throw HttpExceptionEx(result.getError())
            else {
                ordersDao.insertAllOrders(result.payload!!.toDataBaseModel(email))
                id = result.payload!!.id
            }

        }
        return id
    }

    suspend fun refreshOrder(token:String?, email:String) {
        try {
            withContext(Dispatchers.IO) {

                var result = coffeeApi.getLastOrderForUserAsync(composeAuthHeader(token)).await()
                if (result.hasError())
                    throw HttpExceptionEx(result.getError())
                else {
                    ordersDao.insertAllOrders(result.payload!!.toDataBaseModel(email))
                }
            }
            delay(5000)
        }
        catch (ex:Exception){
            Log.d("OrdersRepository.refreshOrder", ex.message?:"")
        }
    }

    suspend fun markAsFinishedForUser(email:String) {
        try {
            withContext(Dispatchers.IO) {
                /*val list = ordersDao.getAllForUserNotFinishedStraight(email)
                list.forEach { it.isFinished = true }
                ordersDao.insertAllOrders(*list.toTypedArray())*/
                ordersDao.markAsFinishedForUser(email)
            }

        }
        catch (ex:Exception){
            Log.d("OrdersRepository.clearQueueOrder", ex.message?:"")
        }
    }

    suspend fun getLastOrderForUserAndPutIntoDB(token:String?, email:String) {
        try {
            withContext(Dispatchers.IO) {
                var result = coffeeApi.getLastOrderForUserAsync(composeAuthHeader(token)).await()
                if (result.hasError())
                    throw HttpExceptionEx(result.getError())
                else {
                    val existing = ordersDao.getByIdStraight(result.payload!!.id)
                    if (existing?.size == 0)
                        ordersDao.insertAllOrders(result.payload!!.toDataBaseModel(email))
                }
            }

        }
        catch (ex:Exception){
            Log.d("OrdersRepository.clearQueueOrder", ex.message?:"")
        }
    }

    suspend fun updateFcmDeviceToken(deviceId : String, fcmToken:String , idToken : String){
        try {
            withContext(Dispatchers.IO){
                val body = PostFcmDeviceTokenBody(deviceId,fcmToken)
                coffeeApi.updateFcmDeviceToken(body, composeAuthHeader(idToken)).await()
            }
        }
        catch (ex:Exception){
            Log.d("OrdersRepository.clearQueueOrder", ex.message?:"")
        }
    }

    suspend fun deleteFcmDeviceTokenOnLogOut(deviceId : String, idToken : String){
        try {
            withContext(Dispatchers.IO){
                coffeeApi.deleteFcmDeviceToken(DeleteFcmDeviceTokenBody(deviceId), composeAuthHeader(idToken))
            }
        }
        catch (ex:Exception){
            Log.d("OrdersRepository.deleteFcmDeviceTokenOnLogOut", ex.message?:"")
        }
    }

}