package com.coffeedose.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coffeedose.domain.OrderDetail

@Entity(tableName = "order_details")
data class OrderDetailDbo (
    @PrimaryKey(autoGenerate = true)
    val id : Int,

    @ColumnInfo(name = "drink_id")
    val drinkId: Int,

    @ColumnInfo(name = "size_id")
    val sizeId:Int,

    @ColumnInfo(name = "order_id")
    val orderId:Int?,

    @ColumnInfo(name = "add_ins")
    val addIns:String?,

    val count:Int
){

    constructor(orderDetail : OrderDetail) : this(
        orderDetail.id,orderDetail.drinkId,orderDetail.sizeId,orderDetail.orderId,orderDetail.addIns,orderDetail.count
    )

    fun toDomainModel() = OrderDetail(
        id,drinkId,sizeId,orderId,addIns,count
    )
}