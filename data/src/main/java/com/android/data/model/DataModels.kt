package com.android.data.model

sealed class DataResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : DataResult<T>()
    data class Error(val exception: Exception) : DataResult<Nothing>()
}

data class TicketDataModel(
    val id: String,
    val name: String,
    val endDate: Long?,
    val unlockDate: Long?,
    val activated: Boolean?,
    val discount: DiscountDataModel,
    val image: String?
)

data class DiscountDataModel(
    val type: Int,
    val description: String
)

data class TicketDetailDataModel(
    val id: String,
    val name: String,
    val description: String,
    val company: String,
    val limit: Int,
    val exchangeLimit: Int,
    val relatedProducts: List<ProductDataModel>,
    val productCode: Long,
    val endDate: Long?,
    val unlockDate: Long?,
    val activated: Boolean?,
    val discount: DiscountDataModel,
    val image: String?
)

data class ProductDataModel(
    val productName: String,
    val image: String
)

