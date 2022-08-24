package com.gotz.domain.repository.calendar

import androidx.annotation.WorkerThread
import com.gotz.domain.model.CalendarModel
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {
    @WorkerThread
    suspend fun create(entity: CalendarModel)

    fun readAll(): Flow<List<CalendarModel>>

    fun readByUid(uid: Int): Flow<CalendarModel>

    @WorkerThread
    suspend fun update(entity: CalendarModel)

    @WorkerThread
    suspend fun delete(uid: Int)
}