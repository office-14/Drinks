package com.office14.coffeedose.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.office14.coffeedose.domain.Order

@Entity(tableName = "orders")
data class OrderDbo (
    @PrimaryKey
    val id : Int,

    @ColumnInfo(name = "status_code")
    val statusCode : String,

    @ColumnInfo(name = "status_name")
    val statusName : String,

    @ColumnInfo(name = "order_number")
    val orderNumber : String,

    @ColumnInfo(name = "total_price")
    val totalPrice : Int,

    @ColumnInfo(name = "owner")
    val owner : String?,

    @ColumnInfo(name = "finished")
    var isFinished : String,

    @ColumnInfo(name = "comment")
    var comment : String?

){
    fun toDomainModel() = Order(
        id, statusCode,statusName,orderNumber,totalPrice,owner,isFinished
    )
}
