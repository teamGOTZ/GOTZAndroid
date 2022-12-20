package com.gotz.data.repository.calendar.local

import androidx.annotation.WorkerThread
import com.gotz.data.model.CalendarEntity
import kotlinx.coroutines.flow.Flow

interface CacheCalendarDataSource{
    @WorkerThread
    suspend fun createCalendar(entity: CalendarEntity)

    fun readAllCalendar(): Flow<List<CalendarEntity>>

    fun readCalendarByUid(uid: Int): Flow<CalendarEntity>

    @WorkerThread
    suspend fun updateCalendar(entity: CalendarEntity)

    @WorkerThread
    suspend fun deleteCalendar(uid: Int)
}