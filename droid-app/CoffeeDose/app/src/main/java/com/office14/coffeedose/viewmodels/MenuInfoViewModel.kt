package com.office14.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.domain.OrderStatus
import com.office14.coffeedose.extensions.mutableLiveData

class MenuInfoViewModel(application: Application, initialOrderId : Int) : AndroidViewModel(application) {

    private val database = CoffeeDatabase.getInstance(application)

    private val _orderId = mutableLiveData(initialOrderId)

    val orderStatus : LiveData<OrderStatus> = Transformations.map(database.ordersDatabaseDao.getById(_orderId.value!!)){
        if (it?.size == 1){
            return@map OrderStatus.getStatusByString(it[0].statusCode)
        }
        return@map OrderStatus.NONE
    }

    val orderDetailsCount = Transformations.map(database.orderDetailsDatabaseDao.getUnAttachedDetails()){
        return@map it.size
    }

    fun updateOrderId(orderId : Int){
        _orderId.value  = orderId
    }

    class Factory(val app: Application,val orderId:Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuInfoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MenuInfoViewModel(app, orderId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}