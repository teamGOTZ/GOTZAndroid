package com.gotz.data.source.calendar

import androidx.annotation.WorkerThread
import com.gotz.data.model.CalendarEntity
import kotlinx.coroutines.flow.Flow

interface CacheCalendarDataSource{
    @WorkerThread
    suspend fun create(entity: CalendarEntity)

    fun readAll(): Flow<List<CalendarEntity>>

    fun readByUid(uid: Int): Flow<CalendarEntity>

    @WorkerThread
    suspend fun update(entity: CalendarEntity)

    @WorkerThread
    suspend fun delete(uid: Int)
}