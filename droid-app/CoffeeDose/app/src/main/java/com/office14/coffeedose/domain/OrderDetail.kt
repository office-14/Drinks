package com.office14.coffeedose.domain

data class OrderDetail(
    val id : Int,
    val drinkId: Int,
    val sizeId:Int,
    val orderId:Int?,
    val count: Int,
    val addIns : List<Addin>
)


class OrderDetailFull(
    val id : Int,
    val drinkId: Int,
    val sizeId:Int,
    val orderId:Int?,
    val count: Int,
    val addIns : List<Addin>,
    val drink:Coffee,
    val size:CoffeeSize

){
    var price: Int

    val orderDetailInner = OrderDetail(
        id,drinkId,sizeId,orderId,count,addIns
    )

    init {
        price = (size.price + addIns.sumBy { addin -> addin.price }) * count
    }

}
