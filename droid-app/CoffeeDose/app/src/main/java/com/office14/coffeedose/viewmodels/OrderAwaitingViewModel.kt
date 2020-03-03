package com.office14.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.repository.OrdersRepository
import com.office14.coffeedose.repository.PreferencesRepository
import kotlinx.coroutines.*

class OrderAwaitingViewModel(application : Application, private val orderId:Int) : AndroidViewModel(application) {

    private val ordersRepository = OrdersRepository(CoffeeDatabase.getInstance(application).ordersDatabaseDao,CoffeeDatabase.getInstance(application).orderDetailsDatabaseDao)

    val order =  Transformations.map(ordersRepository.getOrderById(orderId)){
        it.first()
    }

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        longPollingOrder()
    }


    private fun longPollingOrder(){
        viewModelScope.launch {

            order.observeForever(Observer {
                if (it?.statusName?.toLowerCase() != "ready"){
                    cancel()
                }
            })

            while (isActive) { //keepRefresh.value != false
                ordersRepository.refreshOrder(orderId)
            }
        }


    }

    fun approve(){
        PreferencesRepository.saveLastOrderId(-1)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application,val orderId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderAwaitingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OrderAwaitingViewModel(app,orderId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}