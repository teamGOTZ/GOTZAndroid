package com.gotz.base

import android.app.Application
import com.gotz.database.calendarmemo.CalendarMemoDatabase
import com.gotz.repoository.RoomCalendarMemoRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BaseApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val calendarMemoDatabase by lazy { CalendarMemoDatabase.getInstance(this, applicationScope) }
    val roomCalendarMemoRepositoryImpl by lazy{ RoomCalendarMemoRepositoryImpl(calendarMemoDatabase!!.calendarMemoDao()) }
}