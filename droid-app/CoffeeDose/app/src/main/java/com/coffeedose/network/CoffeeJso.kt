package com.coffeedose.network

import com.coffeedose.database.CoffeeDbo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoffeeJso (
    val id: Int,
    val name: String,
    val description: String,

    @Json(name = "smallest_size_price")
    val smallestSizePrice:Int,

    @Json(name = "photo_url")
    val photoUrl:String
){
    fun toDataBaseModel() = CoffeeDbo(
        id,name,description,smallestSizePrice,photoUrl
    )
}
