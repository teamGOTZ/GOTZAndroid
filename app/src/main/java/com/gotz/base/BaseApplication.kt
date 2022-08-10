package com.gotz.base

import android.app.Application
import com.gotz.database.calendarmemo.CalendarMemoDatabase
import com.gotz.di.cacheLayerModule
import com.gotz.di.dataLayerModule
import com.gotz.di.domainLayerModule
import com.gotz.di.presentationLayerModule
import com.gotz.repoository.RoomCalendarMemoRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class BaseApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val calendarMemoDatabase by lazy { CalendarMemoDatabase.getInstance(this, applicationScope) }
    val roomCalendarMemoRepositoryImpl by lazy{ RoomCalendarMemoRepositoryImpl(calendarMemoDatabase!!.calendarMemoDao()) }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@BaseApplication.applicationContext)
            modules(
                listOf(
                    cacheLayerModule,
                    dataLayerModule,
                    domainLayerModule,
                    presentationLayerModule
                )
            )
        }
    }
}