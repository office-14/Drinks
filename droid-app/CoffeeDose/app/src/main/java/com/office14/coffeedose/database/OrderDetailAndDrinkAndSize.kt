package com.office14.coffeedose.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.office14.coffeedose.domain.OrderDetailFull

class OrderDetailAndDrinkAndSize (

    @Embedded
    val orderDetail : OrderDetailDbo,

    @Relation(
        parentColumn= "drink_id",
        entityColumn = "id"
    )
    val drink : CoffeeDbo,

    @Relation(
        parentColumn = "size_id",
        entityColumn = "id"
    )
    val size : SizeDbo,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            parentColumn = "order_details_id",
            entityColumn = "addin_id",
            value = OrderDetailsAndAddinsCrossRef::class)
    )
    val addIns: List<AddinDbo>
){
    fun toDomainModel() = OrderDetailFull(
        orderDetail.id,
        orderDetail.drinkId,
        orderDetail.sizeId,
        orderDetail.orderId,
        orderDetail.count,
        addIns.map { it.toDomainModel() },
        drink.toDomainModel(),
        size.toDomainModel()
    )
}
