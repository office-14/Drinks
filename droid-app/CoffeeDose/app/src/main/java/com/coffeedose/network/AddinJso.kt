package com.coffeedose.network

import com.coffeedose.database.AddinDbo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddinJso (
    val id : Int,
    val name:String,
    val description:String,
    @Json(name = "photo_url")
    val photoUrl:String,
    val price : Int
){
    fun toDataBaseModel() = AddinDbo(
        id,name,description,photoUrl,price
    )
}