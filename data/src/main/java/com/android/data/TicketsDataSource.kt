package com.android.data

import com.android.data.model.DataResult
import com.android.data.model.TicketDataModel
import com.android.data.model.TicketDetailDataModel
import kotlinx.coroutines.flow.Flow

interface TicketsDataSource {
    suspend fun getTickets(): Flow<DataResult<List<TicketDataModel>>>

    suspend fun activateTicket(ticketId: String): Flow<DataResult<String>>

    suspend fun deactivateTicket(ticketId: String): Flow<DataResult<String>>

    suspend fun getTicketDetail(ticketId: String): Flow<DataResult<TicketDetailDataModel>>
}