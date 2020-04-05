package com.office14.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.domain.Order
import com.office14.coffeedose.repository.OrdersRepository
import com.office14.coffeedose.repository.PreferencesRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.*
import javax.inject.Inject

class OrderAwaitingViewModel @Inject constructor(application : Application, private val ordersRepository : OrdersRepository) : AndroidViewModel(application) {


    //private val orderId = savedStateHandle.get<Int>("orderId") ?: -1

    //private lateinit var

    private var isPolling = false

    /*val order = Transformations.map(ordersRepository.getOrderById(queueOrder.value?.id ?: -1){
        if (it.isNotEmpty()) return@map it[0]
        return@map null
    }*/

    val order : LiveData<Order> = Transformations.map(ordersRepository.getCurrentQueueOrder()) {
        if (it != null) {
            if (!isPolling)
                longPollingOrder(it.id)
            return@map it
        }
        return@map null

    }

    init {
        //longPollingOrder(order.)

        val queueOrders = ordersRepository.getCurrentQueueOrder()
        val orderId = queueOrders.value?.id
    }

    private val _navigateToCoffeeList = MutableLiveData<Boolean>()
    val navigateToCoffeeList : LiveData<Boolean>
        get() = _navigateToCoffeeList


    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private fun longPollingOrder(orderId : Int){

        isPolling = true

        val job = viewModelScope.launch {
            while (isActive) {
                ordersRepository.refreshOrder(orderId, PreferencesRepository.getIdToken())
            }
        }

        order.observeForever(Observer {
            if (it?.statusName?.toLowerCase() == "ready"){
                //PreferencesRepository.saveLastOrderId(-1)
                job.cancel()
                isPolling = false
            }
        })


    }

    fun approve(){
        //PreferencesRepository.saveLastOrderId(-1)
        //PreferencesRepository.saveNavigateToOrderAwaitFrag(false)
        viewModelScope.launch {
            ordersRepository.clearQueueOrder(order.value!!.id)
        }
        _navigateToCoffeeList.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doneNavigation(){
        _navigateToCoffeeList.value = false
    }

}