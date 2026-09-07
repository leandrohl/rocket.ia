package com.example.rocketia

import android.app.Application
import com.example.rocketia.core.di.dataModule
import com.example.rocketia.core.di.domainModule
import com.example.rocketia.core.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RocketAIApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@RocketAIApplication)
            modules (
                dataModule,
                domainModule,
                uiModule,
            )
        }
    }
}