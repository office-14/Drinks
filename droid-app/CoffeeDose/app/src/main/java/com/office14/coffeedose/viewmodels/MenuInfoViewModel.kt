package com.office14.coffeedose.viewmodels

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.coffeedose.R
import com.office14.coffeedose.database.CoffeeDatabase
import com.office14.coffeedose.domain.OrderStatus
import com.office14.coffeedose.repository.OrderDetailsRepository
import com.office14.coffeedose.repository.OrdersRepository

class MenuInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val database = CoffeeDatabase.getInstance(application)

    private val orderDetailsRepository = OrderDetailsRepository(CoffeeDatabase.getInstance(application).orderDetailsDatabaseDao)

    private val ordersRepository = OrdersRepository(database.ordersDatabaseDao,database.ordersQueueDatabaseDao)

    val currentOrderBadgeColor : LiveData<Int> = Transformations.map(ordersRepository.queueOrderStatus){
        return@map when(it){
            OrderStatus.READY -> ContextCompat.getColor(application, R.color.color_green)
            OrderStatus.COOKING -> ContextCompat.getColor(application, R.color.color_yellow)
            OrderStatus.FAILED -> ContextCompat.getColor(application, R.color.color_red)
            else -> ContextCompat.getColor(application, R.color.color_black)
        }
    }

    val orderDetailsCount =  orderDetailsRepository.unAttachedOrderDetailsCount

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuInfoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MenuInfoViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}