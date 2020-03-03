package com.office14.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.domain.OrderDetail
import com.office14.coffeedose.domain.OrderDetailFull
import com.office14.coffeedose.extensions.mutableLiveData
import com.office14.coffeedose.repository.OrderDetailsRepository
import com.office14.coffeedose.repository.OrdersRepository
import kotlinx.coroutines.*

class OrderDetailsViewModel(application : Application) : AndroidViewModel(application) {

    val viewModelJob = Job()
    val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val orderId = mutableLiveData(-1)

    private val orderDetailsRepository = OrderDetailsRepository(CoffeeDatabase.getInstance(application).orderDetailsDatabaseDao)

    private val ordersRepository = OrdersRepository(CoffeeDatabase.getInstance(application).ordersDatabaseDao,CoffeeDatabase.getInstance(application).orderDetailsDatabaseDao)

    val unAttachedOrders = orderDetailsRepository.unAttachedOrderDetails

    val isEmpty = Transformations.map(unAttachedOrders){
        return@map it.isEmpty()
    }

    fun deleteOrderDetailsItem(item : OrderDetailFull){
        viewModelScope.launch {
            orderDetailsRepository.delete(item.orderDetailInner)
        }
    }

    fun confirmOrder(){
        viewModelScope.launch {
           val newOrderId = ordersRepository.createOrder(unAttachedOrders.value ?: listOf())
            orderId.value = newOrderId


            val orderDetails = mutableListOf<OrderDetail>()
            unAttachedOrders.value?.forEach {
                val orderDetail = OrderDetail(
                    it.orderDetailInner.id,
                    it.orderDetailInner.drinkId,
                    it.orderDetailInner.sizeId,
                    orderId.value,
                    it.orderDetailInner.count,
                    listOf()
                )
                orderDetails.add(orderDetail)
            }
            orderDetailsRepository.insertAll(orderDetails)
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

     fun clearOrderDetails(){
         viewModelScope.launch {
             orderDetailsRepository.deleteUnAttached()
         }
     }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderDetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OrderDetailsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}