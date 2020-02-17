package com.coffeedose.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://mars.udacity.com/" //TODO set base url

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface CoffeeApiService {
    @GET("drinks")
    fun getDrinks():Deferred<ResponseContainer<List<CoffeeJso>>>
}

object CoffeeApi {
    val retrofitService : CoffeeApiService by lazy { retrofit.create(CoffeeApiService::class.java) }
}


/*enum class MarsApiFilter(val value: String) { SHOW_RENT("rent"), SHOW_BUY("buy"), SHOW_ALL("all") }

*//**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 *//*


*//**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 *//*


*//**
 * A public interface that exposes the [getProperties] method
 *//*
interface MarsApiService {
    *//**
     * Returns a Coroutine [Deferred] [List] of [MarsProperty] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     *//*
    @GET("realestate")
    fun getProperties(@Query("filter") type: String):
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<List<MarsProperty>>
}

*//**
 * A public Api object that exposes the lazy-initialized Retrofit service
 *//*
object MarsApi {
    val retrofitService : MarsApiService by lazy { retrofit.create(MarsApiService::class.java) }
}*/
