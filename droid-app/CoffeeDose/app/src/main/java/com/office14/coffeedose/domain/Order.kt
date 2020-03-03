package com.office14.coffeedose.domain

data class Order (
    var id : Int,
    val statusCode : String,
    val statusName : String,
    val orderNumber : String,
    val totalPrice : Int
)