package com.android.domain.usecase

import com.android.domain.TicketRepository
import kotlinx.coroutines.flow.Flow

interface ActivateTicket {
    suspend fun execute(ticketId: String): Flow<String>
}

class ActivateTicketImpl(private val ticketRepository: TicketRepository) : ActivateTicket {
    override suspend fun execute(ticketId: String): Flow<String> =
        ticketRepository.activateTicket(ticketId)
}
