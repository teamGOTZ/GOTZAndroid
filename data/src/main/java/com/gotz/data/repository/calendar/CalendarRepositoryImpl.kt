package com.gotz.data.repository.calendar

import com.gotz.data.mapper.DataCalendarMapper.toMapper
import com.gotz.data.repository.calendar.local.CacheCalendarDataSource
import com.gotz.domain.model.CalendarModel
import com.gotz.domain.repository.calendar.CalendarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CalendarRepositoryImpl(
    private val cacheCalendarDataSource: CacheCalendarDataSource
): CalendarRepository {
    override suspend fun createCalendar(entity: CalendarModel) {
        cacheCalendarDataSource.createCalendar(entity.toMapper())
    }

    override fun readAllCalendar(): Flow<List<CalendarModel>> =
        cacheCalendarDataSource.readAllCalendar().map {list ->
            val mutableList = mutableListOf<CalendarModel>()
            list.map { cacheCalendarEntity ->
                mutableList.add(cacheCalendarEntity.toMapper())
            }
            mutableList
        }

    override fun readCalendarByUid(uid: Int): Flow<CalendarModel>  =
        cacheCalendarDataSource.readCalendarByUid(uid).map { cacheCalendarEntity ->
            cacheCalendarEntity.toMapper()
        }

    override suspend fun updateCalendar(entity: CalendarModel) {
        cacheCalendarDataSource.updateCalendar(entity.toMapper())
    }

    override suspend fun deleteCalendar(uid: Int) {
        cacheCalendarDataSource.deleteCalendar(uid)
    }
}