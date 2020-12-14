package com.android.domain.usecase

import com.android.domain.TicketRepository
import com.android.domain.model.TicketDomainModel
import kotlinx.coroutines.flow.Flow

interface GetTickets {
    suspend fun execute(): Flow<List<TicketDomainModel>>
}

class GetTicketsImpl(private val ticketRepository: TicketRepository) : GetTickets {
    override suspend fun execute(): Flow<List<TicketDomainModel>> = ticketRepository.getTickets()
}
