package com.office14.coffeedose.network

import com.office14.coffeedose.database.OrderDbo
import com.office14.coffeedose.domain.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LastOrderJso (

    val id : Int,

    @Json(name = "status_code")
    val statusCode : String,

    @Json(name = "status_name")
    val statusName : String,

    @Json(name = "order_number")
    val orderNumber : String,

    @Json(name = "total_price")
    val totalPrice : Int,

    val comment : String?,

    @Json(name = "drinks")
    val drinkDetais: List<DrinkDetailsJso>


){
    fun toDataBaseModel(owner:String) = OrderDbo(
        id, statusCode,statusName,orderNumber,totalPrice,owner,"false",comment
    )

    fun toOrderInfoDomainModel() : OrderInfo {
        val orderDetailsFullList : MutableList<OrderDetailFull> = mutableListOf()

        drinkDetais.forEach { drinkDetail ->
            orderDetailsFullList.add(OrderDetailFull(
                drinkDetail.drink.id,0,0,0,drinkDetail.count,null,drinkDetail.addIns.map { it.toDomainModel() }, drinkDetail.drink.toDomainModel(), drinkDetail.size.toDomainModel(),drinkDetail.price
            ))
        }

        return OrderInfo(id,statusCode,statusName,orderNumber,totalPrice,comment,orderDetailsFullList)
    }
}


@JsonClass(generateAdapter = true)
data class DrinkDetailsJso (
    val drink : DrinkShortJso,

    @Json(name = "drink_size")
    val size : SizeShortJso,

    @Json(name = "add-ins")
    val addIns : List<AddinShortJso>,

    val count: Int,

    val price: Int
)

@JsonClass(generateAdapter = true)
data class DrinkShortJso(
    val id: Int,
    val name: String,

    @Json(name = "photo_url")
    val photoUrl:String
){
    fun toDomainModel() = Coffee(id,name,name,0,photoUrl)
}

@JsonClass(generateAdapter = true)
data class SizeShortJso (
    var id: Int,
    var name: String,
    var volume: String
){
    fun toDomainModel() = CoffeeSize(id,0,volume,name,0)
}

@JsonClass(generateAdapter = true)
data class AddinShortJso (
    val id : Int,
    val name:String
)
{
    fun toDomainModel() = Addin(id,name,name,name,0)
}
