package com.gotz

import android.app.Application
import com.gotz.di.dataLayerModule
import com.gotz.di.domainLayerModule
import com.gotz.di.presentationLayerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GotzApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GotzApplication.applicationContext)
            modules(
                listOf(
                    dataLayerModule,
                    domainLayerModule,
                    presentationLayerModule
                )
            )
        }
    }
}