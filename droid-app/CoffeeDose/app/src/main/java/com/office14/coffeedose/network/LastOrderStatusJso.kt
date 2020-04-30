package com.office14.coffeedose.network

import com.office14.coffeedose.domain.LastOrderStatus
import com.squareup.moshi.Json


data class LastOrderStatusJso (
    val id : Int,

    @Json(name = "status_code")
    val statusCode : String,

    @Json(name = "status_name")
    val statusName : String
){
    fun toDomainModel() = LastOrderStatus(id,statusCode,statusName)
}