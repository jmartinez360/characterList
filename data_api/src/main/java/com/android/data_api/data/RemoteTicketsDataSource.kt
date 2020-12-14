package com.android.data_api.data

import com.android.data.TicketsDataSource
import com.android.data.model.DataResult
import com.android.data.model.TicketDataModel
import com.android.data.model.TicketDetailDataModel
import com.android.data_api.api.TicketsApiService
import com.android.data_api.mapper.ApiToDataSourceMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
@FlowPreview
class RemoteTicketsDataSource(
    private val apiService: TicketsApiService,
    private val mapper: ApiToDataSourceMapper
) : TicketsDataSource, DataSource() {

    private val ticketsChannel = ConflatedBroadcastChannel<DataResult<List<TicketDataModel>>>()

    override suspend fun getTickets(): Flow<DataResult<List<TicketDataModel>>> {
        val response = apiService.getTickets()
        val apiResult = handleApiResponse(response)
        val result = mapper.mapTicketListResult(apiResult)
        ticketsChannel.offer(result)
        return ticketsChannel.asFlow()
    }

    override suspend fun activateTicket(ticketId: String): Flow<DataResult<String>> {
        return flow {
            addRandomDelay()
            emit(DataResult.Success(ticketId))
        }
    }

    override suspend fun deactivateTicket(ticketId: String): Flow<DataResult<String>> {
        return flow { emit(DataResult.Success(ticketId)) }
    }

    override suspend fun getTicketDetail(ticketId: String): Flow<DataResult<TicketDetailDataModel>> {
        val response = apiService.getTickets()
        val apiResult = handleApiResponse(response)
        val result = mapper.mapTicketListAndGetRequired(ticketId, apiResult)
        return flow {
            addRandomDelay()
            emit(result)
        }
    }
}