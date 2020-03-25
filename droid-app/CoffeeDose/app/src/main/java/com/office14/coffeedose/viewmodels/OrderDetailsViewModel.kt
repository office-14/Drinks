package com.office14.coffeedose.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.domain.OrderDetail
import com.office14.coffeedose.domain.OrderDetailFull
import com.office14.coffeedose.network.HttpExceptionEx
import com.office14.coffeedose.repository.OrderDetailsRepository
import com.office14.coffeedose.repository.OrdersRepository
import com.office14.coffeedose.repository.PreferencesRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(application : Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //val orderId = mutableLiveData(-1)

    private var orderDetailsRepository:OrderDetailsRepository  = OrderDetailsRepository(CoffeeDatabase.getInstance(application).orderDetailsDatabaseDao)

    private val ordersRepository = OrdersRepository(CoffeeDatabase.getInstance(application).ordersDatabaseDao,CoffeeDatabase.getInstance(application).ordersQueueDatabaseDao)

    private val _navigateOrderAwaiting = MutableLiveData<Boolean>()
    val navigateOrderAwaiting : LiveData<Boolean>
        get() = _navigateOrderAwaiting

    val unAttachedOrders = orderDetailsRepository.unAttachedOrderDetails

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String>
        get() = _errorMessage

    private val _needLogin = MutableLiveData<Boolean>()
    val needLogIn : LiveData<Boolean>
        get() = _needLogin

    val isEmpty = Transformations.map(unAttachedOrders){
        return@map it.isEmpty()
    }

    val hasOrderInQueue = Transformations.map(ordersRepository.getCurrentQueueOrder()){
        return@map it != null
    }

    fun deleteOrderDetailsItem(item : OrderDetailFull){
        viewModelScope.launch {
            orderDetailsRepository.delete(item.orderDetailInner)
        }
    }

    fun confirmOrder(){
        viewModelScope.launch {
            try {

                val newOrderId = ordersRepository.createOrder(unAttachedOrders.value ?: listOf(),PreferencesRepository.getIdToken())

                //orderId.value = newOrderId

                val orderDetails = mutableListOf<OrderDetail>()
                unAttachedOrders.value?.forEach {
                    val orderDetail = OrderDetail(
                        it.orderDetailInner.id,
                        it.orderDetailInner.drinkId,
                        it.orderDetailInner.sizeId,
                        newOrderId,
                        it.orderDetailInner.count,
                        listOf()
                    )
                    orderDetails.add(orderDetail)
                }
                orderDetailsRepository.insertAll(orderDetails)

                _navigateOrderAwaiting.value = true
            }
            catch (responseEx: HttpExceptionEx) {
                _errorMessage.value = responseEx.error.title
            } catch (ex: Exception) {
                if (ex.message?.contains("401") == true){
                    _needLogin.value = true
                    _errorMessage.value = "Необходима авторизация"
                }
                else
                    _errorMessage.value = "Ошибка получения данных"
            }
        }
    }

    fun doneLogin(){
        _needLogin.value = false
    }

    fun hideErrorMessage(){
        _errorMessage.value = null
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

    fun doneNavigating(){
        _navigateOrderAwaiting.value = false
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