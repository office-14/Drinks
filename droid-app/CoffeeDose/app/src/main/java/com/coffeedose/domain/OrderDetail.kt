package com.coffeedose.domain

data class OrderDetail(
    val id : Int,
    val drinkId: Int,
    val sizeId:Int,
    val orderId:Int?,
    val addIns:String?,
    val count: Int
)