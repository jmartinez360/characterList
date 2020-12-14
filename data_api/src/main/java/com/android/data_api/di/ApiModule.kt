package com.android.data_api.di

import com.android.data.TicketsDataSource
import com.android.data_api.api.TicketsApiService
import com.android.data_api.data.RemoteTicketsDataSource
import com.android.data_api.mapper.ApiToDataSourceMapper
import com.android.data_api.mapper.TicketsApiToDataSourceMapperImpl
import com.android.data_api.utils.FakeInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single { provideTicketDataSourceMapper() }
    single { provideTicketsDataSource(get(), get()) }
    single { provideHttpClient() }
    single { provideRetrofit(get()) }
    single { provideTicketsApiService(get()) }
}

private fun provideTicketDataSourceMapper(): ApiToDataSourceMapper {
    return TicketsApiToDataSourceMapperImpl()
}

private fun provideTicketsDataSource(
    apiService: TicketsApiService,
    mapper: ApiToDataSourceMapper
): TicketsDataSource {
    return RemoteTicketsDataSource(apiService, mapper)
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .client(okHttpClient)
        .baseUrl("http://fake.com/v1/")
        .build()
}

fun provideHttpClient(): OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder()
    return httpClientBuilder.addInterceptor(FakeInterceptor()).build()
}

private fun provideTicketsApiService(retrofit: Retrofit): TicketsApiService =
    retrofit.create(TicketsApiService::class.java)
