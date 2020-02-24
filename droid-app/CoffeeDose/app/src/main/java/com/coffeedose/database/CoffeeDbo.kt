package com.coffeedose.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coffeedose.domain.Coffee

@Entity(tableName = "drinks")
data class CoffeeDbo(

    @PrimaryKey
    var id: Int,

    var name: String,

    var description: String,

    @ColumnInfo(name = "smallest_size_price")
    val smallestSizePrice:Int,

    @ColumnInfo(name = "photo_url")
    var photoUrl:String
){
    fun toDomainModel() = Coffee(
        id,name,description,smallestSizePrice,photoUrl
    )
}
