package com.office14.coffeedose.database

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.office14.coffeedose.domain.OrderDetail


@Entity(
    tableName = "order_details"
    /*foreignKeys = [
        ForeignKey(
            entity = CoffeeDbo::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("drink_id"),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = SizeDbo::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("size_id"),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = OrderDbo::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("order_id"),
            onDelete = CASCADE
        )
    ]*/
)
// Using only for INSERT new rows
data class OrderDetailDbo (

    @PrimaryKey(autoGenerate = true)
    val id : Int,

    @ColumnInfo(name = "drink_id")
    val drinkId: Int,

    /*@ColumnInfo(name = "drink_name")
    val drinkName: String?,*/

    @ColumnInfo(name = "size_id")
    val sizeId:Int,

    /*@ColumnInfo(name = "size_name")
    val sizeName: String?,*/

    @ColumnInfo(name = "order_id")
    var orderId:Int?,

    @ColumnInfo(name = "owner")
    var owner : String?,

    val count:Int
){

    constructor(orderDetail : OrderDetail) : this(
        orderDetail.id,orderDetail.drinkId,orderDetail.sizeId,orderDetail.orderId,null,orderDetail.count
        //, orderDetail.addIns.map { AddinDbo(it) }
    )

    fun toDomainModel() = OrderDetail(
        id,drinkId,sizeId,orderId,count,owner,listOf()
    )
}


@Entity(primaryKeys = ["order_details_id", "addin_id"])
data class OrderDetailsAndAddinsCrossRef(

    @ForeignKey(
        entity = OrderDetailDbo::class,
        parentColumns = ["id"],
        childColumns = ["order_details_id"],
        onDelete = CASCADE
    )
    @ColumnInfo(name = "order_details_id")
    val orderDetailsId: Int,

    @ColumnInfo(name = "addin_id")
    val addinId: Int
)

class OrderDetailsContainer(orderDetail: OrderDetail){

    val orderDetails : OrderDetailDbo = OrderDetailDbo(orderDetail)

    val addIns : List<AddinDbo> = orderDetail.addIns.map { AddinDbo(it) }

}
