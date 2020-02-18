package com.coffeedose.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TestPayload (val payload : List<CoffeeJso>)
