package com.android.domain.di

import com.android.domain.TicketRepository
import com.android.domain.usecase.ActivateTicket
import com.android.domain.usecase.ActivateTicketImpl
import com.android.domain.usecase.DeactivateTicket
import com.android.domain.usecase.DeactivateTicketImpl
import com.android.domain.usecase.GetTicketDetail
import com.android.domain.usecase.GetTicketDetailImpl
import com.android.domain.usecase.GetTickets
import com.android.domain.usecase.GetTicketsImpl
import org.koin.dsl.module

val domainModule = module {
    single<GetTickets> { provideGetTicketsUseCase(get()) }
    single<ActivateTicket> { provideActivateTicketUseCase(get()) }
    single<DeactivateTicket> { provideDeactivateTicketUseCase(get()) }
    single<GetTicketDetail> { provideGetTicketDetailUseCase(get()) }
}

private fun provideGetTicketsUseCase(repository: TicketRepository) =
    GetTicketsImpl(repository)

private fun provideActivateTicketUseCase(repository: TicketRepository) =
    ActivateTicketImpl(repository)

private fun provideDeactivateTicketUseCase(repository: TicketRepository) =
    DeactivateTicketImpl(repository)

private fun provideGetTicketDetailUseCase(repository: TicketRepository) =
    GetTicketDetailImpl(repository)