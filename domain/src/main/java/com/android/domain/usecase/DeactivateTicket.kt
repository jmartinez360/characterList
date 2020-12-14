package com.android.domain.usecase

import com.android.domain.TicketRepository
import kotlinx.coroutines.flow.Flow

interface DeactivateTicket {
    suspend fun execute(ticketId: String): Flow<String>
}

class DeactivateTicketImpl(private val ticketRepository: TicketRepository) : DeactivateTicket {
    override suspend fun execute(ticketId: String): Flow<String> =
        ticketRepository.deactivateTicket(ticketId)
}