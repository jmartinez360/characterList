package com.android.data_api.mapper

import com.android.data.model.DataResult
import com.android.data.model.DiscountDataModel
import com.android.data.model.ProductDataModel
import com.android.data.model.TicketDataModel
import com.android.data.model.TicketDetailDataModel
import com.android.data_api.model.ApiResult
import com.android.data_api.model.DiscountApiModel
import com.android.data_api.model.ProductApiModel
import com.android.data_api.model.TicketApiModel

interface ApiToDataSourceMapper {

    fun mapTicketListResult(result: ApiResult<List<TicketApiModel>>): DataResult<List<TicketDataModel>>

    fun mapTicketListAndGetRequired(ticketId: String, result: ApiResult<List<TicketApiModel>>): DataResult<TicketDetailDataModel>
}

class TicketsApiToDataSourceMapperImpl : ApiToDataSourceMapper {

    override fun mapTicketListResult(result: ApiResult<List<TicketApiModel>>): DataResult<List<TicketDataModel>> {
        return when (result) {
            is ApiResult.Success -> DataResult.Success(mapTicketList(result.data))
            is ApiResult.Error -> DataResult.Error(result.exception)
        }
    }

    private fun mapTicketList(list: List<TicketApiModel>) = list.map { mapSingleTicket(it) }

    private fun mapSingleTicket(ticketApiModel: TicketApiModel) = TicketDataModel(
        id = ticketApiModel.id!!,
        name = ticketApiModel.name!!,
        endDate = ticketApiModel.endDate,
        unlockDate = ticketApiModel.unlockDate,
        discount = mapDiscount(ticketApiModel.discount!!),
        image = ticketApiModel.image,
        activated = ticketApiModel.activated
    )

    private fun mapDiscount(discountApiModel: DiscountApiModel) = DiscountDataModel(
        type = discountApiModel.type!!,
        description = discountApiModel.description!!
    )

    override fun mapTicketListAndGetRequired(
        ticketId: String,
        result: ApiResult<List<TicketApiModel>>
    ): DataResult<TicketDetailDataModel> {
        return when (result) {
            is ApiResult.Success -> DataResult.Success(
                findAndMapRequiredTicket(ticketId, result.data
                )
            )
            is ApiResult.Error -> DataResult.Error(result.exception)
        }
    }

    private fun findAndMapRequiredTicket(
        ticketId: String,
        ticketList: List<TicketApiModel>
    ): TicketDetailDataModel {
        val requiredTicket = ticketList.find { it.id == ticketId } ?: TicketApiModel()
        return mapTicketDetail(requiredTicket)
    }

    private fun mapTicketDetail(ticket: TicketApiModel) = TicketDetailDataModel(
        id = ticket.id!!,
        description = ticket.description!!,
        discount = mapDiscount(ticket.discount!!),
        image = ticket.image,
        activated = ticket.activated,
        name = ticket.name!!,
        endDate = ticket.endDate,
        company = ticket.company!!,
        exchangeLimit = ticket.exchangeLimit!!,
        limit = ticket.limit!!,
        productCode = ticket.productCode!!,
        relatedProducts = ticket.relatedProducts!!.map { mapRelatedProducts(it) },
        unlockDate = ticket.unlockDate!!
    )

    private fun mapRelatedProducts(product: ProductApiModel) = ProductDataModel(
        productName = product.productName,
        image = product.image!!
    )

}