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

    @GET("/api/user/orders/last")
    @Headers("Cache-Control: no-cache")
    fun getLastOrderForUserAsync(@Header("Authorization") authHeader:String):Deferred<ResponseContainer<LastOrderJso>>

    @GET("/api/user/orders/last/status")
    @Headers("Cache-Control: no-cache")
    fun getLastOrderStatusForUserAsync(@Header("Authorization") authHeader:String):Deferred<ResponseContainer<LastOrderStatusJso>>

    @POST("/api/orders")
    fun createOrderAsync(@Body body : CreateOrderBody, @Header("Authorization") authHeader:String) :Deferred<ResponseContainer<CreateOrderResponseJso>>

    @POST("/api/user/device-tokens/update")
    fun updateFcmDeviceToken(@Body body : PostFcmDeviceTokenBody, @Header("Authorization") authHeader:String) :Deferred<ResponseContainer<Any>>

    @POST("/api/user/device-tokens/delete")
    fun deleteFcmDeviceToken(@Body body : DeleteFcmDeviceTokenBody, @Header("Authorization") authHeader:String):Deferred<ResponseContainer<Any>>
}