package com.android.data_api.model

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
}

data class TicketApiModel(
    val id: String? = "",
    val name: String? = "",
    val description: String? = "",
    val company: String? = "",
    val limit: Int? = 0,
    val exchangeLimit: Int? = 0,
    val relatedProducts: List<ProductApiModel>? = emptyList(),
    val productCode: Long? = 0,
    val endDate: Long? = 0L,
    val unlockDate: Long? = 0L,
    val activated: Boolean? = false,
    val discount: DiscountApiModel? = DiscountApiModel(),
    val image: String? = ""
)

data class DiscountApiModel(
    val type: Int? = -1,
    val description: String? = ""
)

data class ProductApiModel(
    val productName: String,
    val image: String? = ""
)