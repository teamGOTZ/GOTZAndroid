package com.wedid.repoository

import com.wedid.database.calendarmemo.CalendarMemo
import com.wedid.database.calendarmemo.CalendarMemoDao
import kotlinx.coroutines.flow.Flow

class RoomCalendarMemoRepositoryImpl(private val calendarMemoDao: CalendarMemoDao): RoomCalendarMemoRepository {
    override val allRoomCalendar: Flow<List<CalendarMemo>> = calendarMemoDao.getAll()

    override fun uidRoomCalendar(uid: Int): Flow<CalendarMemo> {
        return calendarMemoDao.getUidItem(uid)
    }

    override suspend fun insert(calendarMemo: CalendarMemo) {
        calendarMemoDao.insert(calendarMemo)
    }

    override suspend fun update(calendarMemo: CalendarMemo) {
        calendarMemoDao.updateUidItem(calendarMemo.title, calendarMemo.content, calendarMemo.isAllDay, calendarMemo.start, calendarMemo.end, calendarMemo.uid)
    }

    override suspend fun delete(uid: Int) {
        calendarMemoDao.deleteUidItem(uid)
    }
}