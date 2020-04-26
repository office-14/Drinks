package com.office14.coffeedose.domain

import com.office14.coffeedose.extensions.deepEqualToIgnoreOrder

data class OrderDetail(
    val id : Int,
    val drinkId: Int,
    val sizeId:Int,
    val orderId:Int?,
    val count: Int,
    val owner: String?,
    val addIns : List<Addin>
){
    fun checkEquals(other:OrderDetail): Boolean
    {
        if (drinkId != other.drinkId) return false
        if (sizeId != other.sizeId) return false
        if (!checkAddInsEquals(other.addIns)) return false
        return true
    }

    private fun checkAddInsEquals(other:List<Addin>) : Boolean {

        if (addIns.size != other.size) return false
        addIns.forEach{ addIn ->
            if (other.firstOrNull{ it.id == addIn.id } == null) return false
        }
        return true
    }
}


class OrderDetailFull(
    val id : Int,
    val drinkId: Int,
    val sizeId:Int,
    val orderId:Int?,
    val count: Int,
    val owner: String?,
    val addIns : List<Addin>,
    val drink:Coffee,
    val size:CoffeeSize

){
    var price: Int

    val orderDetailInner = OrderDetail(
        id,drinkId,sizeId,orderId,count,owner,addIns
    )

    init {
        price = (size.price + addIns.sumBy { addin -> addin.price }) * count
    }

}
