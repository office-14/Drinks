package com.coffeedose.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseContainer <T>(
    var payload : T?,
    var message : String?,

    @Json(name = "stack_trace")
    var stackTrace : String?
)