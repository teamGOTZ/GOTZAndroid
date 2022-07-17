package com.gotz.repoository

import androidx.annotation.WorkerThread
import com.gotz.database.calendarmemo.CalendarMemo
import kotlinx.coroutines.flow.Flow

interface RoomCalendarMemoRepository {
    val allRoomCalendar: Flow<List<CalendarMemo>>

    fun uidRoomCalendar(uid:Int): Flow<CalendarMemo>

    @WorkerThread
    suspend fun insert(calendarMemo: CalendarMemo)

    @WorkerThread
    suspend fun update(calendarMemo: CalendarMemo)

    @WorkerThread
    suspend fun delete(uid: Int)
}