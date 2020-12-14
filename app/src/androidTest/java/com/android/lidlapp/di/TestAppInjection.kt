package com.android.lidlapp.di

import com.android.data.di.dataModule
import com.android.domain.di.domainModule

fun generateTestAppComponents(testUrl: String) = listOf(
    generateTestApiModule(testUrl), dataModule, domainModule, viewModelModule, utilsModule
)