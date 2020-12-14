package com.android.lidlapp.app

import com.android.lidlapp.LidlApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class TestLidlApplication : LidlApplication() {

    override fun onCreate() {
        startKoin {
            androidContext(this@TestLidlApplication)
            modules(getModules())
        }
    }

    override fun getModules(): List<Module> {
        return emptyList()
    }
}