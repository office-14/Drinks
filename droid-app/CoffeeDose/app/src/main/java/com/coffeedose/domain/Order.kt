package com.coffeedose.domain

import java.util.*

data class Order (
    var id : Int,
    val statusCode : String,
    val statusName : String,
    var createdDate : Date,
    val finishDate : Date?,
    val orderNumber : String,
    val totalPrice : Int
)