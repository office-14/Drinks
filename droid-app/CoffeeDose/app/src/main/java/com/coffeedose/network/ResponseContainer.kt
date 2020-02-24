package com.coffeedose.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseContainer <T>(
    var payload : T
)