package com.office14.coffeedose.domain

data class OrderInfo(
    val id : Int,
    val statusCode : String,
    val statusName : String,
    val orderNumber : String,
    val totalPrice : Int,
    val comment : String?,
    val drinks : List<OrderDetailFull>
){
    val actualComment = comment ?: "Не указан"

    val orderStatus  = OrderStatus.getStatusByString(statusCode)

    val orderStatusHumanized = when(orderStatus){
        OrderStatus.COOKING -> "Готовится"
        OrderStatus.READY -> "Готов"
        OrderStatus.FAILED -> "Не успешен"
        else -> "Не определен"
    }
}
