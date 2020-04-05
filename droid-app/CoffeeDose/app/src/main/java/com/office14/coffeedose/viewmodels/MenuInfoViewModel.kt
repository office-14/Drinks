package com.office14.coffeedose.viewmodels

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.coffeedose.R
import com.office14.coffeedose.domain.OrderStatus
import com.office14.coffeedose.repository.OrderDetailsRepository
import com.office14.coffeedose.repository.OrdersRepository
import javax.inject.Inject

class MenuInfoViewModel @Inject constructor(application: Application,private val orderDetailsRepository : OrderDetailsRepository,
                                            private val ordersRepository : OrdersRepository) : AndroidViewModel(application) {

    val currentOrderBadgeColor : LiveData<Int> = Transformations.map(ordersRepository.queueOrderStatus){
        return@map when(it){
            OrderStatus.READY -> ContextCompat.getColor(application, R.color.color_green)
            OrderStatus.COOKING -> ContextCompat.getColor(application, R.color.color_yellow)
            OrderStatus.FAILED -> ContextCompat.getColor(application, R.color.color_red)
            else -> ContextCompat.getColor(application, R.color.color_black)
        }
    }

    val orderDetailsCount =  orderDetailsRepository.unAttachedOrderDetailsCount
}