package com.office14.coffeedose.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.office14.coffeedose.domain.CoffeeSize

@Entity(tableName = "drink_size")
data class SizeDbo (
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "drink_id")
    var drinkId : Int,

    var volume: String,
    var name: String,
    var price : Int

){
    fun toDomainModel() = CoffeeSize(
        id,drinkId,volume,name,price
    )
}