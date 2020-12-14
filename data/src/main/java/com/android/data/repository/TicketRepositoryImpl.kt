package com.android.data.repository

import com.android.data.TicketsDataSource
import com.android.data.mapper.TicketDataToDomainModelMapper
import com.android.data.model.DataResult
import com.android.data.model.TicketDataModel
import com.android.data.model.TicketDetailDataModel
import com.android.domain.TicketRepository
import com.android.domain.model.TicketDetailDomainModel
import com.android.domain.model.TicketDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TicketRepositoryImpl(
    private val dataSource: TicketsDataSource,
    private val mapper: TicketDataToDomainModelMapper
) : BaseRepository(), TicketRepository {

    override suspend fun getTickets() =
        dataSource.getTickets().map { mapTicketsResponseOrThrowException(it) }

    private fun mapTicketsResponseOrThrowException(response: DataResult<List<TicketDataModel>>): List<TicketDomainModel> {
        return mapResultOrThrow(response, mapFunction = { mapper.mapTicketList(it) })
    }

    override suspend fun activateTicket(ticketId: String) =
        dataSource.activateTicket(ticketId).map { mapActivateResponseOrThrowException(it) }

    private fun mapActivateResponseOrThrowException(response: DataResult<String>): String {
        return mapResultOrThrow(response, mapFunction = { it })
    }

    override suspend fun deactivateTicket(ticketId: String) =
        dataSource.deactivateTicket(ticketId).map { mapDeactivateResponseOrThrowException(it) }

    private fun mapDeactivateResponseOrThrowException(response: DataResult<String>): String {
        return mapResultOrThrow(response, mapFunction = { it })
    }

    override suspend fun getTicketDetail(ticketId: String): Flow<TicketDetailDomainModel> =
        dataSource.getTicketDetail(ticketId).map { mapTicketDetailResponseOrThrowException(it) }

    private fun mapTicketDetailResponseOrThrowException(response: DataResult<TicketDetailDataModel>): TicketDetailDomainModel {
        return mapResultOrThrow(response, mapFunction = { mapper.mapTicketDetail(it) })
    }
}