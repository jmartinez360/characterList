package com.android.lidlapp.model

data class TicketModel(
    val id: String,
    val name: String,
    val endDate: Long?,
    val unlockDate: Long?,
    var activated: Boolean,
    val discount: DiscountModel,
    val image: String?
)

data class DiscountModel(
    val type: Int,
    val description: String
) {
    companion object {
        const val LOW_DISCOUNT = 1
        const val MEDIUM_DISCOUNT = 2
    }
}

data class TicketDetailModel(
    val id: String,
    val name: String,
    val description: String,
    val company: String,
    val limit: Int,
    val exchangeLimit: Int,
    val relatedProducts: List<ProductModel>,
    val productCode: Long,
    val endDate: Long?,
    val unlockDate: Long?,
    val activated: Boolean?,
    val discount: DiscountModel,
    val image: String?
)

data class ProductModel(
    val productName: String,
    val image: String
)