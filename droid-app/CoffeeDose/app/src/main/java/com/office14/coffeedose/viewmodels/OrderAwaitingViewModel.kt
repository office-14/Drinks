package com.office14.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.coffeedose.BuildConfig
import com.office14.coffeedose.domain.Order
import com.office14.coffeedose.extensions.mutableLiveData
import com.office14.coffeedose.repository.OrderDetailsRepository
import com.office14.coffeedose.repository.OrdersRepository
import com.office14.coffeedose.repository.PreferencesRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class OrderAwaitingViewModel @Inject constructor(application : Application, private val ordersRepository : OrdersRepository, private val ordersDetailsRepository : OrderDetailsRepository) : AndroidViewModel(application) {


    private var orderId = mutableLiveData(-1)

    //private lateinit var

    private var isPolling = false

    /*val order = Transformations.map(ordersRepository.getOrderById(queueOrder.value?.id ?: -1){
        if (it.isNotEmpty()) return@map it[0]
        return@map null
    }*/

    val orderDetails = Transformations.map(orderId){
        return@map ordersDetailsRepository.getOrderDetailsByOrderId(it).value
    }

    val order : LiveData<Order> = Transformations.map(ordersRepository.getCurrentQueueOrder()) {
        if (it != null) {
            if (!isPolling)
                longPollingOrder(it.id)
            return@map it
        }
        return@map null

    }

    init {
        //initOrderId()

        //val queueOrders = ordersRepository.getCurrentQueueOrder()
        //val orderId = queueOrders.value?.id
    }

    private val _navigateToCoffeeList = MutableLiveData<Boolean>()
    val navigateToCoffeeList : LiveData<Boolean>
        get() = _navigateToCoffeeList


    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private fun longPollingOrder(_orderId : Int){

        isPolling = true

        val job = viewModelScope.launch {

            orderId.value = getOrderId()

            while (isActive) {
                ordersRepository.refreshOrder(_orderId, PreferencesRepository.getIdToken())
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

    suspend fun getOrderId() : Int{
        var result = -1
        withContext(Dispatchers.IO) {
            val order = ordersRepository.getCurrentQueueOrderNormal()
            result = order!!.id
        }
        return result
    }

    private fun initOrderId(){
        viewModelScope.launch {
            val order = ordersRepository.getCurrentQueueOrderNormal()
            order?.let { orderId.value = it.id }
        }
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