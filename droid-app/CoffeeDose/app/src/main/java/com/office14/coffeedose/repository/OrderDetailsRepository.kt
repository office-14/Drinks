package com.office14.coffeedose.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.office14.coffeedose.database.OrderDetailAndDrinkAndSize
import com.office14.coffeedose.database.OrderDetailDao
import com.office14.coffeedose.database.OrderDetailDbo
import com.office14.coffeedose.database.OrderDetailsContainer
import com.office14.coffeedose.domain.OrderDetail
import com.office14.coffeedose.domain.OrderDetailFull
import com.office14.coffeedose.repository.PreferencesRepository.EMPTY_STRING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderDetailsRepository(private val orderDetailsDao : OrderDetailDao) {

    val unAttachedOrderDetails = Transformations.map(orderDetailsDao.getUnAttachedDetails()){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    suspend fun unattachedOrderDetailsForUser(email:String) : List<OrderDetailFull> {
        val result : MutableList<OrderDetailFull> = mutableListOf()

        withContext(Dispatchers.IO) {
            val list = orderDetailsDao.getUnAttachedDetailsForUserStraight(email)
            list.forEach {
                result.add(it.toDomainModel())
            }
        }
        return result
    }

    suspend fun unattachedOrderDetailsWithoutUser() : List<OrderDetailFull> {
        val result : MutableList<OrderDetailFull> = mutableListOf()

        withContext(Dispatchers.IO) {
            val list = orderDetailsDao.getUnAttachedDetailsWithoutUserStraight()
            list.forEach {
                result.add(it.toDomainModel())
            }
        }
        return result
    }



    fun getOrderDetailsByOrderId(orderId:Int) = Transformations.map(orderDetailsDao.getDetailsByOrderId(orderId)){ itDbo ->
        itDbo.map { it.toDomainModel() }
    }

    fun unAttachedOrderDetails(email:LiveData<String>) : LiveData<List<OrderDetailFull>>{
        val result = MediatorLiveData<List<OrderDetailFull>>()
        val uod = orderDetailsDao.getUnAttachedDetails()

        val update = {
            if(email.value == EMPTY_STRING){
                result.value = uod?.value?.asSequence()?.filter{ it.orderDetail.owner == null}?.map { it.toDomainModel() }?.toList() ?: listOf()
            }
            else
                result.value = uod?.value?.asSequence()?.filter{ it.orderDetail.owner == email.value}?.map { it.toDomainModel() }?.toList() ?: listOf()
        }

        result.addSource(email){ update.invoke() }
        result.addSource(uod){ update.invoke() }


        return result
    }

    suspend fun unAttachedOrderDetailsStraight(email:String) : List<OrderDetailFull>{

        val result : MutableList<OrderDetailFull> = mutableListOf()

        withContext(Dispatchers.IO){
            val list = if (email != EMPTY_STRING) orderDetailsDao.getUnAttachedDetailsForUserStraight(email) else orderDetailsDao.getUnAttachedDetailsWithoutUserStraight()
            list.forEach { result.add(it.toDomainModel()) }
        }

        return result

    }


    fun unAttachedOrderDetailsCount(email:LiveData<String>) : LiveData<Int>{
        val result = MediatorLiveData<Int>()
        val uod = orderDetailsDao.getUnAttachedDetails()

        val update = {
            if(email.value == EMPTY_STRING){
                result.value = uod?.value?.count(){ it.orderDetail.owner == null} ?: 0
            }
            else
                result.value = uod?.value?.count(){ it.orderDetail.owner == email.value} ?: 0
        }

        result.addSource(email){ update.invoke() }
        result.addSource(uod){ update.invoke() }


        return result
    }

    suspend fun delete(oderDetails : OrderDetail){
        try {
            withContext(Dispatchers.IO) {
                orderDetailsDao.delete(OrderDetailDbo(oderDetails))
            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.deleteById", ex.message?:"")
        }
    }

    suspend fun deleteUnAttached(){
        try {
            withContext(Dispatchers.IO) {
                orderDetailsDao.deleteUnAttached()
            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.deleteUnAttached", ex.message?:"")
        }
    }

    suspend fun insertAll(orderDetails:List<OrderDetail>){
        try {
            withContext(Dispatchers.IO) {
                orderDetailsDao.insertOrderDetailsAndAddIns(*orderDetails.map {  OrderDetailsContainer(it)}.toTypedArray())
            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.insertAll", ex.message?:"")
        }
    }

    suspend fun insertNew(orderDetail:OrderDetail){
        try {
            withContext(Dispatchers.IO) {
                val container = OrderDetailsContainer(orderDetail)
                val email = PreferencesRepository.getUserEmail()
                container.orderDetails.owner = if (email == EMPTY_STRING) null else email
                orderDetailsDao.insertOrderDetailsAndAddIns(container)
            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.insertAll", ex.message?:"")
        }
    }

    suspend fun mergeIn(orderDetail:OrderDetail){
        try {
            withContext(Dispatchers.IO) {

                val email = PreferencesRepository.getUserEmail()
                val existingOrderDetails : List<OrderDetail>
                existingOrderDetails = if (email == EMPTY_STRING)
                    orderDetailsDao.getUnAttachedDetailsWithoutUserStraight().map { it.toDomainModel().orderDetailInner }
                else
                    orderDetailsDao.getUnAttachedDetailsForUserStraight(email!!).map { it.toDomainModel().orderDetailInner }

                val existingDetail = existingOrderDetails.firstOrNull{ it.checkEquals(orderDetail) }
                if (existingDetail == null){
                    val container = OrderDetailsContainer(orderDetail)
                    if (email != EMPTY_STRING){
                        container.orderDetails.owner = email
                    }

                    orderDetailsDao.insertOrderDetailsAndAddIns(container)
                }
                else
                    orderDetailsDao.updateCountWithOrderDetailsId(existingDetail.id,existingDetail.count + orderDetail.count)

            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.mergeIn", ex.message?:"")
        }
    }


    suspend fun updateUnattachedOrderDetailsWithEmail(email:String){
        try {
            withContext(Dispatchers.IO) {

                /*val unattached = orderDetailsDao.getUnAttachedDetails()
                if (unattached?.value?.size != 0) {
                    orderDetailsDao.deleteByEmail(email)
                }*/
                orderDetailsDao.updateUnattachedOrderDetailsWithEmail(email)
            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.updateUnattachedOrderDetailsWithEmail", ex.message?:"")
        }
    }

    suspend fun updateAttachedOrderDetailsWithOrderId(email:String, orderId:Int){
        try {
            withContext(Dispatchers.IO) {
                orderDetailsDao.updateAttachedOrderDetailsWithOrderId(email,orderId)
            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.updateAttachedOrderDetailsWithOrderId", ex.message?:"")
        }
    }

    suspend fun deleteOrderDetailsByEmail(email:String){
        try {
            withContext(Dispatchers.IO) {
                orderDetailsDao.deleteByEmail(email)
            }
        }
        catch (ex:Exception){
            Log.d("OrderDetailsRepository.deleteOrderDetailsByEmail", ex.message?:"")
        }
    }
}