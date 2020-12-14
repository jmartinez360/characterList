package com.android.domain.model

data class TicketDomainModel(
    val id: String,
    val name: String,
    val endDate: Long?,
    val unlockDate: Long?,
    val activated: Boolean? = false,
    val discount: DiscountDomainModel,
    val image: String?
)

data class DiscountDomainModel(
    val type: Int,
    val description: String
)

data class TicketDetailDomainModel(
    val id: String,
    val name: String,
    val description: String,
    val company: String,
    val limit: Int,
    val exchangeLimit: Int,
    val relatedProducts: List<ProductDomainModel>,
    val productCode: Long,
    val endDate: Long?,
    val unlockDate: Long?,
    val activated: Boolean?,
    val discount: DiscountDomainModel,
    val image: String?
)

data class ProductDomainModel(
    val productName: String,
    val image: String
)
