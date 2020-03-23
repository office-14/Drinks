package com.office14.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.repository.OrdersRepository
import com.office14.coffeedose.repository.PreferencesRepository
import kotlinx.coroutines.*

class OrderAwaitingViewModel(application : Application, private val orderId:Int) : AndroidViewModel(application) {

    private val ordersRepository = OrdersRepository(CoffeeDatabase.getInstance(application).ordersDatabaseDao,CoffeeDatabase.getInstance(application).orderDetailsDatabaseDao)

    val order =  Transformations.map(ordersRepository.getOrderById(orderId)){
        it.first()
    }

    private val _naviagateToCoffeeList = MutableLiveData<Boolean>()
    val naviagateToCoffeeList : LiveData<Boolean>
        get() = _naviagateToCoffeeList


    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        if (PreferencesRepository.getLastOrderId() != -1)
            longPollingOrder()
    }


    private fun longPollingOrder(){

        val job = viewModelScope.launch {
            while (isActive) { //keepRefresh.value != false
                ordersRepository.refreshOrder(orderId, PreferencesRepository.getIdToken())
            }
        }

        order.observeForever(Observer {
            if (it?.statusName?.toLowerCase() == "ready"){
                PreferencesRepository.saveLastOrderId(-1)
                job.cancel()
            }
        })


    }

    fun approve(){
        PreferencesRepository.saveLastOrderId(-1)
        //PreferencesRepository.saveNavigateToOrderAwaitFrag(false)
        _naviagateToCoffeeList.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doneNavigation(){
        _naviagateToCoffeeList.value = false
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