package com.coffeedose.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseContainer <T>(
    val payload : T?,
    val type : String?,
    val title : String?,
    val status : Int?,
    val traceId : String?,
    val detail : String?
){
    fun hasError(): Boolean = payload == null

    fun getError()  = ResponseError(
        type?:"unknown type",title?:"Error",status?:500,traceId?:"",detail?:""
    )
}


data class ResponseError(
    val type : String,
    val title : String,
    val status : Int,
    val traceId : String,
    val detail : String
)