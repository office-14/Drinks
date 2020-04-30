package com.office14.coffeedose.domain

data class Order (
    var id : Int,
    val statusCode : String,
    val statusName : String,
    val orderNumber : String,
    val totalPrice : Int,
    val owner : String?,
    val isFinished : String
)

enum class OrderStatus {
    NONE, COOKING, READY, FAILED;

    companion object{
        fun getStatusByString(status: String) : OrderStatus{
            return when(status.toLowerCase()){
                "cooking" -> OrderStatus.COOKING
                "ready" -> OrderStatus.READY
                else -> OrderStatus.NONE
            }
        }
    }


}