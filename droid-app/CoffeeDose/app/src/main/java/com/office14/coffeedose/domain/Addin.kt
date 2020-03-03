package com.office14.coffeedose.domain

data class Addin(
    val id : Int,
    val name:String,
    val description:String,
    val photoUrl:String,
    val price : Int,
    var isSelected:Boolean = false
)