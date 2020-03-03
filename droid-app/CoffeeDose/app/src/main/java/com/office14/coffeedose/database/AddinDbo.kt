package com.office14.coffeedose.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.office14.coffeedose.domain.Addin


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

    constructor(addInDomain : Addin) : this(
        addInDomain.id,addInDomain.name,addInDomain.description,addInDomain.photoUrl,addInDomain.price
    )

    fun toDomainModel() = Addin(
        id,name,description,photoUrl,price
    )
}