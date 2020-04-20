package com.office14.coffeedose.network

import com.office14.coffeedose.database.OrderDbo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderJso (

    val id : Int,

    @Json(name = "status_code")
    val statusCode : String,

    @Json(name = "status_name")
    val statusName : String,

    @Json(name = "order_number")
    val orderNumber : String,

    @Json(name = "total_price")
    val totalPrice : Int
){
    fun toDataBaseModel(owner:String) = OrderDbo(
        id, statusCode,statusName,orderNumber,totalPrice,owner,"false"
    )
}