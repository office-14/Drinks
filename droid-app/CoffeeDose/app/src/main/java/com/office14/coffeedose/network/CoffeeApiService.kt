package com.office14.coffeedose.network

import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface CoffeeApiService {
    @GET("drinks")
    fun getDrinksAsync():Deferred<ResponseContainer<List<CoffeeJso>>>

    @GET("/api/drinks/{drinkId}/sizes")
    fun getSizesByDrinkIdAsync(@Path(value = "drinkId", encoded = true) drinkId : Int):Deferred<ResponseContainer<List<SizeJso>>>

    @GET("add-ins")
    fun getAddinsAsync():Deferred<ResponseContainer<List<AddinJso>>>

    @GET("/api/orders/{orderId}")
    fun getOrderByIdAsync(@Path(value = "orderId", encoded = true) orderId : Int, @Header("Authorization") authHeader:String):Deferred<ResponseContainer<OrderJso>>

    @GET("/api/user/orders/last")
    fun getLastOrderForUserAsync(@Header("Authorization") authHeader:String):Deferred<ResponseContainer<OrderJso>>

    @POST("/api/orders")
    fun createOrderAsync(@Body body : CreateOrderBody, @Header("Authorization") authHeader:String) :Deferred<ResponseContainer<OrderJso>>

    @POST("/api/user/device-tokens/update")
    fun updateFcmDeviceToken(@Body body : PostFcmDeviceTokenBody, @Header("Authorization") authHeader:String) :Deferred<ResponseContainer<Any>>

    @POST("/api/user/device-tokens/delete")
    fun deleteFcmDeviceToken(@Body body : DeleteFcmDeviceTokenBody, @Header("Authorization") authHeader:String):Deferred<ResponseContainer<Any>>
}