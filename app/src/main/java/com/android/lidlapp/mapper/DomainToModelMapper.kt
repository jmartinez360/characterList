package com.android.lidlapp.mapper

import com.android.domain.model.DiscountDomainModel
import com.android.domain.model.ProductDomainModel
import com.android.domain.model.TicketDetailDomainModel
import com.android.domain.model.TicketDomainModel
import com.android.lidlapp.model.DiscountModel
import com.android.lidlapp.model.ProductModel
import com.android.lidlapp.model.TicketDetailModel
import com.android.lidlapp.model.TicketModel

interface DomainToModelMapper {

    fun mapToTicketModel(ticketDomainModel: TicketDomainModel): TicketModel
    fun mapToTicketModelList(tickets: List<TicketDomainModel>): List<TicketModel>
    fun mapToTicketDetailModel(ticket: TicketDetailDomainModel): TicketDetailModel
}

class DomainToModelMapperImpl : DomainToModelMapper {
    override fun mapToTicketModel(ticketDomainModel: TicketDomainModel) =
        TicketModel(
            id = ticketDomainModel.id,
            name = ticketDomainModel.name,
            endDate = ticketDomainModel.endDate,
            unlockDate = ticketDomainModel.unlockDate,
            activated = ticketDomainModel.activated!!,
            image = ticketDomainModel.image,
            discount = mapDiscount(ticketDomainModel.discount)
        )

    override fun mapToTicketModelList(tickets: List<TicketDomainModel>) =
        tickets.map { mapToTicketModel(it) }

    private fun mapDiscount(discountDomainModel: DiscountDomainModel) = DiscountModel(
        type = discountDomainModel.type,
        description = discountDomainModel.description
    )

    override fun mapToTicketDetailModel(ticket: TicketDetailDomainModel) = TicketDetailModel(
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

    private fun mapRelatedProducts(product: ProductDomainModel) = ProductModel(
        productName = product.productName,
        image = product.image
    )
}