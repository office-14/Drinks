package com.coffeedose.network

import com.coffeedose.database.CoffeeDbo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoffeeJso (
    var id: Int,
    var name: String,
    var description: String,

    @Json(name = "photo_url")
    var photoUrl:String
)

fun List<CoffeeJso>.asDatabaseModel():List<CoffeeDbo> {
    return map { CoffeeDbo(
        id = it.id,
        name = it.name,
        description = it.description,
        photoUrl = it.photoUrl
    ) }
}