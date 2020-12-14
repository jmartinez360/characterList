package com.android.data.mapper

import com.android.data.model.DiscountDataModel
import com.android.data.model.ProductDataModel
import com.android.data.model.TicketDataModel
import com.android.data.model.TicketDetailDataModel
import com.android.domain.model.DiscountDomainModel
import com.android.domain.model.ProductDomainModel
import com.android.domain.model.TicketDetailDomainModel
import com.android.domain.model.TicketDomainModel

interface TicketDataToDomainModelMapper {
    fun mapSingleTicket(ticket: TicketDataModel): TicketDomainModel
    fun mapTicketList(tickets: List<TicketDataModel>): List<TicketDomainModel>
    fun mapTicketDetail(ticket: TicketDetailDataModel): TicketDetailDomainModel
}

class TicketDataToDomainModelMapperImpl : TicketDataToDomainModelMapper {
    override fun mapSingleTicket(ticket: TicketDataModel) = TicketDomainModel(
        id = ticket.id,
        name = ticket.name,
        endDate = ticket.endDate,
        unlockDate = ticket.unlockDate,
        activated = ticket.activated,
        image = ticket.image,
        discount = mapDiscount(ticket.discount)
    )

    override fun mapTicketList(tickets: List<TicketDataModel>) = tickets.map { mapSingleTicket(it) }

    private fun mapDiscount(discountDataModel: DiscountDataModel) = DiscountDomainModel(
        type = discountDataModel.type,
        description = discountDataModel.description
    )

    override fun mapTicketDetail(ticket: TicketDetailDataModel) = TicketDetailDomainModel(
        id = ticket.id,
        description = ticket.description,
        discount = mapDiscount(ticket.discount),
        image = ticket.image,
        activated = ticket.activated,
        name = ticket.name,
        endDate = ticket.endDate,
        company = ticket.company,
        exchangeLimit = ticket.exchangeLimit,
        limit = ticket.limit,
        productCode = ticket.productCode,
        relatedProducts = ticket.relatedProducts.map { mapRelatedProducts(it) },
        unlockDate = ticket.unlockDate!!
    )

    private fun mapRelatedProducts(product: ProductDataModel) = ProductDomainModel(
        productName = product.productName,
        image = product.image
    )
}