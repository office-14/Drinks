package com.office14.coffeedose.network

import com.office14.coffeedose.domain.OrderDetailFull
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateOrderBody(var drinks : List<DrinkJso> = listOf()){


    fun fillWithOrders(orderDetails:List<OrderDetailFull>){
        val _drinks  = mutableListOf<DrinkJso>()

        for (order in orderDetails){
            repeat(order.count) {
                _drinks.add(DrinkJso(order.drinkId, order.sizeId, order.addIns.map { it.id }))
            }
        }

        drinks = _drinks
    }
}

@JsonClass(generateAdapter = true)
data class DrinkJso(
    @Json(name = "drink_id")
    val drinkId :Int,

    @Json(name = "size_id")
    val sizeId :Int,

    @Json(name = "add-ins")
    val addins: List<Int>
)