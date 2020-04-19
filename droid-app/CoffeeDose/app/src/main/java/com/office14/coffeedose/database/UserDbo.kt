package com.office14.coffeedose.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.office14.coffeedose.domain.User

@Entity(tableName = "users")
data class UserDbo(
    @PrimaryKey
    val email : String,

    @ColumnInfo(name = "display_name")
    val displayName:String,

    @ColumnInfo(name = "photo_url")
    val photoUrl:String,

    @ColumnInfo(name = "id_token")
    val idToken:String,

    @ColumnInfo(name = "fcm_token")
    val fcmToken:String?
){

    constructor(user : User) : this(
        user.email,user.displayName,user.photoUrl,user.idToken,user.fcmToken
    )

    fun toDomainModel() = User(
        email,displayName,photoUrl,idToken,fcmToken
    )
}