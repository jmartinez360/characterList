package com.android.lidlapp.di

import com.android.data.TicketsDataSource
import com.android.data_api.api.TicketsApiService
import com.android.data_api.data.RemoteTicketsDataSource
import com.android.data_api.mapper.ApiToDataSourceMapper
import com.android.data_api.mapper.TicketsApiToDataSourceMapperImpl
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun generateTestApiModule(testUrl: String) = module {
    single { provideMockWebServer() }
    single { provideRetrofit(testUrl) }
    single { provideTicketsApiService(get()) }
    single { provideTicketsDataSource(get()) }
}

private fun provideMockWebServer() = MockWebServer()

private fun provideRetrofit(url: String) = Retrofit.Builder()
    .baseUrl(url)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

private fun provideTicketsApiService(retrofit: Retrofit) =
    retrofit.create(TicketsApiService::class.java)

private fun provideTicketsDataSource(
    apiService: TicketsApiService
): TicketsDataSource {
    return RemoteTicketsDataSource(apiService, TicketsApiToDataSourceMapperImpl())
}
