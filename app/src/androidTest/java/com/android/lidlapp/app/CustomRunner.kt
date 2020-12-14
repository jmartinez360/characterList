package com.android.lidlapp.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class CustomRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader,
        className: String,
        context: Context
    ): Application {
        return super.newApplication(
            classLoader,
            TestLidlApplication::class.java.name,
            context
        )
    }
}