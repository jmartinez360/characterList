package com.android.domain

import com.android.domain.model.TicketDetailDomainModel
import com.android.domain.model.TicketDomainModel
import kotlinx.coroutines.flow.Flow

interface TicketRepository {

    suspend fun getTickets(): Flow<List<TicketDomainModel>>
    suspend fun activateTicket(ticketId: String): Flow<String>
    suspend fun deactivateTicket(ticketId: String): Flow<String>
    suspend fun getTicketDetail(ticketId: String): Flow<TicketDetailDomainModel>
}