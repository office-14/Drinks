package com.coffeedose.network

import com.coffeedose.repository.PreferencesRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ConnectionSpec
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(PreferencesRepository.getBaseUrl())
    .build()

interface CoffeeApiService {
    @GET("drinks")
    fun getDrinksAsync():Deferred<ResponseContainer<List<CoffeeJso>>>

    @GET("/api/drinks/{drinkId}/sizes")
    fun getSizesByDrinkIdAsync(@Path(value = "drinkId", encoded = true) drinkId : Int):Deferred<ResponseContainer<List<SizeJso>>>

    @GET("add-ins")
    fun getAddinsAsync():Deferred<ResponseContainer<List<AddinJso>>>

    @GET("/api/orders/{orderId}")
    fun getOrderById(@Path(value = "orderId", encoded = true) orderId : Int):Deferred<ResponseContainer<OrderJso>>

    @POST("/api/orders")
    fun createOrder( @Body body : CreateOrderBody) :Deferred<ResponseContainer<OrderJso>>


}

object CoffeeApi {
    val retrofitService : CoffeeApiService by lazy { retrofit.create(CoffeeApiService::class.java) }
}