package com.gotz.domain.repository.calendar

import androidx.annotation.WorkerThread
import com.gotz.domain.model.CalendarModel
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {
    @WorkerThread
    suspend fun createCalendar(entity: CalendarModel)

    fun readAllCalendar(): Flow<List<CalendarModel>>

    fun readCalendarByUid(uid: Int): Flow<CalendarModel>

    @WorkerThread
    suspend fun updateCalendar(entity: CalendarModel)

    @WorkerThread
    suspend fun deleteCalendar(uid: Int)
}