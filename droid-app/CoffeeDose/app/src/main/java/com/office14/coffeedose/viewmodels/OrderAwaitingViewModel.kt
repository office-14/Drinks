package com.office14.coffeedose.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.office14.coffeedose.domain.Order
import com.office14.coffeedose.domain.OrderInfo
import com.office14.coffeedose.extensions.mutableLiveData
import com.office14.coffeedose.repository.OrderDetailsRepository
import com.office14.coffeedose.repository.OrdersRepository
import com.office14.coffeedose.repository.PreferencesRepository
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

class OrderAwaitingViewModel @Inject constructor(application : Application, private val ordersRepository : OrdersRepository, private val ordersDetailsRepository : OrderDetailsRepository) : AndroidViewModel(application) {


    private var orderId = mutableLiveData(-1)
    private val email = mutableLiveData(PreferencesRepository.EMPTY_STRING)

    val queueOrderStatus = ordersRepository.queueOrderStatus(email)

    var orderInfo = mutableLiveData<OrderInfo>(null)

    val order : LiveData<Order> = Transformations.map(ordersRepository.getCurrentQueueOrderByUser(email)) {
        if (it != null) {
            return@map it
        }
        return@map null

    }


    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    private val _navigateToCoffeeList = MutableLiveData<Boolean>()
    val navigateToCoffeeList : LiveData<Boolean>
        get() = _navigateToCoffeeList


    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        email.value = PreferencesRepository.getUserEmail()
        getOrderInfo()
    }

    private fun getOrderInfo(){
        try {
            viewModelScope.launch {
                orderInfo.value = ordersRepository.getLastOrderInfo(PreferencesRepository.getIdToken()!!)
                _isLoading.value = false
            }
        }
        catch (ex:Exception){
            Log.d("OrderAwaitingViewModel.getOrderInfo",ex.message)
        }
    }

    private suspend fun getOrderId() : Int{
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
            ordersRepository.markAsFinishedForUser(email.value!!)
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