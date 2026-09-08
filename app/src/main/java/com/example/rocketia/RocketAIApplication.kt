package com.example.rocketia

import android.app.Application
import com.example.rocketia.core.di.dataModule
import com.example.rocketia.core.di.domainModule
import com.example.rocketia.core.di.uiModule
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RocketAIApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )


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