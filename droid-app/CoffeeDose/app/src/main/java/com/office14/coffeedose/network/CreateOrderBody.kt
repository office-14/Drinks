package com.office14.coffeedose.network

import com.office14.coffeedose.domain.OrderDetailFull
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateOrderBody(

    @Json(name = "comment")
    val comment:String?,

    @Json(name = "drinks")
    var drinks : List<DrinkJso> = listOf()
){
    fun fillWithOrders(orderDetails:List<OrderDetailFull>){
        drinks = listOf(*orderDetails.map { order ->  DrinkJso(order.drinkId, order.sizeId, order.addIns.map { it.id },order.count) }.toTypedArray())
    }
}

@JsonClass(generateAdapter = true)
data class DrinkJso(
    @Json(name = "drink_id")
    val drinkId :Int,

    @Json(name = "size_id")
    val sizeId :Int,

    @Json(name = "add-ins")
    val addins: List<Int>,

    val count:Int
)