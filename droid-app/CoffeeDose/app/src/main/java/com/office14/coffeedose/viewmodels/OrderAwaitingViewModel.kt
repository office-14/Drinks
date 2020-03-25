package com.office14.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.domain.Order
import com.office14.coffeedose.repository.OrdersRepository
import com.office14.coffeedose.repository.PreferencesRepository
import kotlinx.coroutines.*

class OrderAwaitingViewModel(application : Application) : AndroidViewModel(application) {

    private val database = CoffeeDatabase.getInstance(application)

    private val ordersRepository = OrdersRepository(database.ordersDatabaseDao,database.ordersQueueDatabaseDao)

    private var isPolling = false

    /*val order = Transformations.map(ordersRepository.getOrderQueue()){
        it
    }*/

    val order : LiveData<Order> = Transformations.map(CoffeeDatabase.getInstance(application).ordersQueueDatabaseDao.getAll()){ itDbo ->
        if (itDbo.size == 1){
            if (!isPolling)
                longPollingOrder(itDbo[0].toDomainModel().id)
            return@map itDbo[0].toDomainModel()
        }
        return@map null
    }

    private val _navigateToCoffeeList = MutableLiveData<Boolean>()
    val navigateToCoffeeList : LiveData<Boolean>
        get() = _navigateToCoffeeList


    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        //if (order.value != null)

    }


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
        _navigateToCoffeeList.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doneNavigation(){
        _navigateToCoffeeList.value = false
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderAwaitingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OrderAwaitingViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}