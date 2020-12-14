package com.android.data.di

import com.android.data.TicketsDataSource
import com.android.data.mapper.TicketDataToDomainModelMapper
import com.android.data.mapper.TicketDataToDomainModelMapperImpl
import com.android.data.repository.TicketRepositoryImpl
import com.android.domain.TicketRepository
import org.koin.dsl.module

val dataModule = module {
    single { provideDataToDomainMapper() }
    single { provideTicketRepository(get(), get()) }
}

private fun provideDataToDomainMapper(): TicketDataToDomainModelMapper =
    TicketDataToDomainModelMapperImpl()

private fun provideTicketRepository(
    dataSource: TicketsDataSource,
    mapper: TicketDataToDomainModelMapper
): TicketRepository =
    TicketRepositoryImpl(dataSource, mapper)