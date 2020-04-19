package com.office14.coffeedose.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DeleteFcmDeviceTokenBody(
    @Json(name = "device_id")
    val deviceId : String
)

@JsonClass(generateAdapter = true)
class PostFcmDeviceTokenBody(
    @Json(name = "device_id")
    val deviceId : String,

    @Json(name = "token")
    val token : String
)