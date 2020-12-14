package com.android.lidlapp.di

import com.android.lidlapp.utils.CoilImageLoader
import com.android.lidlapp.utils.ImageLoader
import com.android.lidlapp.utils.TicketDateUtils
import com.android.lidlapp.utils.TicketDateUtilsImpl
import org.koin.dsl.module

val utilsModule = module {
    single<TicketDateUtils> { provideTicketDateUtils() }
    single<ImageLoader> { provideCoilImageLoader() }
}

private fun provideTicketDateUtils() =
    TicketDateUtilsImpl()

private fun provideCoilImageLoader() =
    CoilImageLoader()