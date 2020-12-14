package com.android.domain.usecase

import com.android.domain.TicketRepository
import com.android.domain.model.TicketDetailDomainModel
import kotlinx.coroutines.flow.Flow

interface GetTicketDetail {
    suspend fun execute(ticketId: String): Flow<TicketDetailDomainModel>
}

class GetTicketDetailImpl(private val ticketRepository: TicketRepository) : GetTicketDetail {
    override suspend fun execute(ticketId: String) = ticketRepository.getTicketDetail(ticketId)
}