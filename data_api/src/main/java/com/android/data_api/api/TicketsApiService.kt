package com.android.data_api.api

import com.android.data_api.model.TicketApiModel
import retrofit2.Response
import retrofit2.http.GET

interface TicketsApiService {

    @GET("tickets")
    suspend fun getTickets(): Response<List<TicketApiModel>>
}