package com.office14.coffeedose.domain

class User(
    val email : String,
    val displayName : String,
    val photoUrl : String,
    var idToken : String,
    var fcmToken : String?
)