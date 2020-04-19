package com.office14.coffeedose.viewmodels

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.coffeedose.R
import com.office14.coffeedose.domain.OrderStatus
import com.office14.coffeedose.domain.User
import com.office14.coffeedose.extensions.mutableLiveData
import com.office14.coffeedose.repository.OrderDetailsRepository
import com.office14.coffeedose.repository.OrdersRepository
import com.office14.coffeedose.repository.PreferencesRepository
import com.office14.coffeedose.repository.PreferencesRepository.EMPTY_STRING
import com.office14.coffeedose.repository.UsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuInfoViewModel @Inject constructor(application: Application,private val orderDetailsRepository : OrderDetailsRepository,
                                            private val ordersRepository : OrdersRepository,
                                            private val usersRepository:UsersRepository) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val email  = mutableLiveData(EMPTY_STRING)
    private var app : Application = application

    val orderDetailsCount = orderDetailsRepository.unAttachedOrderDetailsCount(email)
    private val unattachedOrderDetails = orderDetailsRepository.unAttachedOrderDetails

    init {
        refreshOrderDetailsByUser()
    }

    val currentOrderBadgeColor = Transformations.map(ordersRepository.queueOrderStatus(email)){
        return@map when(it){
            OrderStatus.READY -> ContextCompat.getColor(app, R.color.color_green)
            OrderStatus.COOKING -> ContextCompat.getColor(app, R.color.color_yellow)
            OrderStatus.FAILED -> ContextCompat.getColor(app, R.color.color_red)
            else -> ContextCompat.getColor(app, R.color.color_black)
        }
    }


    var user : User? = null

    val isLoggined = user != null

    fun refreshOrderDetailsByUser(){
        viewModelScope.launch {
            val oldEmail = email.value
            email.value = PreferencesRepository.getUserEmail()!!
            if (email.value != EMPTY_STRING){
                user = usersRepository.getUserByEmail(email.value!!)
                if (oldEmail == EMPTY_STRING) {
                    if (unattachedOrderDetails.value != null && unattachedOrderDetails.value?.size != 0 )
                        orderDetailsRepository.deleteOrderDetailsByEmail(email.value!!) //TODO update
                    orderDetailsRepository.updateUnattachedOrderDetailsWithEmail(email.value!!)
                }
            } else {
                user = null
            }
        }
    }

    fun updateUser(){
        viewModelScope.launch {
            user?.let {
                usersRepository.updateUser(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
