package com.office14.coffeedose.network

import com.office14.coffeedose.repository.PreferencesRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

    @POST("/api/orders")
    fun createOrderAsync(@Body body : CreateOrderBody, @Header("Authorization") authHeader:String) :Deferred<ResponseContainer<OrderJso>>
}