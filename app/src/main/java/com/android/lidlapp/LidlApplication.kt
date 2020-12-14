package com.android.lidlapp

import android.app.Application
import com.android.data.di.dataModule
import com.android.data_api.di.apiModule
import com.android.domain.di.domainModule
import com.android.lidlapp.di.utilsModule
import com.android.lidlapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class LidlApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDependencyInjection()
    }

    private fun setupDependencyInjection() {
        startKoin {
            androidContext(this@LidlApplication)
            modules(getModules())
        }
    }

    open fun getModules() = listOf(apiModule, dataModule, domainModule, viewModelModule, utilsModule)
}