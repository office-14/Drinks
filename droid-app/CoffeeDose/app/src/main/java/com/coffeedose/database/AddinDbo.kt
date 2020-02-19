package com.coffeedose.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coffeedose.domain.Addin


@Entity(tableName = "add_ins")
data class AddinDbo(
    @PrimaryKey
    val id : Int,

    val name:String,

    val description:String,

    @ColumnInfo(name = "photo_url")
    val photoUrl:String,

    val price : Int
){
    fun toDomainModel() = Addin(
        id,name,description,photoUrl,price
    )
}