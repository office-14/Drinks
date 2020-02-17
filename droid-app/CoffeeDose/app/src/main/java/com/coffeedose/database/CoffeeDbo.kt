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

    @ColumnInfo(name = "photo_url")
    var photoUrl:String
)

fun List<CoffeeDbo>.asDomainModel() : List<Coffee> {
    return map {
        Coffee(
            id = it.id,
            name = it.name,
            description = it.description,
            photoUrl = it.photoUrl
        )
    }
}
