package com.office14.coffeedose.domain

data class Coffee(
    val id: Int,
    val name: String,
    val description: String,
    val smallestSizePrice : Int,
    val photoUrl:String
)