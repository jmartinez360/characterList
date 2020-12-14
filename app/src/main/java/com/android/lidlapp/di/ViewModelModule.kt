package com.android.lidlapp.di

import com.android.domain.usecase.ActivateTicket
import com.android.domain.usecase.DeactivateTicket
import com.android.domain.usecase.GetTicketDetail
import com.android.domain.usecase.GetTickets
import com.android.lidlapp.mapper.DomainToModelMapper
import com.android.lidlapp.mapper.DomainToModelMapperImpl
import com.android.lidlapp.viewModel.TicketDetailViewModel
import com.android.lidlapp.viewModel.TicketDetailViewModelImpl
import com.android.lidlapp.viewModel.TicketListViewModel
import com.android.lidlapp.viewModel.TicketListViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<TicketListViewModel> { provideTicketListViewModel(get(), get(), get(), get()) }
    viewModel<TicketDetailViewModel> { provideTicketDetailViewModel(get(), get()) }
    single<DomainToModelMapper> { provideTicketMapper() }
}

private fun provideTicketListViewModel(
    getTickets: GetTickets,
    activateTicket: ActivateTicket,
    deactivateTicket: DeactivateTicket,
    mapper: DomainToModelMapper
) =
    TicketListViewModelImpl(getTickets, activateTicket, deactivateTicket, mapper)

private fun provideTicketDetailViewModel(
    getTicketDetail: GetTicketDetail,
    mapper: DomainToModelMapper
) =
    TicketDetailViewModelImpl(getTicketDetail, mapper)

private fun provideTicketMapper() =
    DomainToModelMapperImpl()