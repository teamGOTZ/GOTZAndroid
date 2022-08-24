package com.gotz.data.repository.calendar

import com.gotz.data.mapper.DataCalendarMapper.toMapper
import com.gotz.data.model.CalendarEntity
import com.gotz.data.source.calendar.CacheCalendarDataSource
import com.gotz.domain.model.CalendarModel
import com.gotz.domain.repository.calendar.CalendarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CalendarRepositoryImpl(
    private val cacheCalendarDataSource: CacheCalendarDataSource
): CalendarRepository {
    override suspend fun create(entity: CalendarModel) {
        cacheCalendarDataSource.create(entity.toMapper())
    }

    override fun readAll(): Flow<List<CalendarModel>> =
        cacheCalendarDataSource.readAll().map {list ->
            val mutableList = mutableListOf<CalendarModel>()
            list.map { cacheCalendarEntity ->
                mutableList.add(cacheCalendarEntity.toMapper())
            }
            mutableList
        }

    override fun readByUid(uid: Int): Flow<CalendarModel>  =
        cacheCalendarDataSource.readByUid(uid).map { cacheCalendarEntity ->
            cacheCalendarEntity.toMapper()
        }

    override suspend fun update(entity: CalendarModel) {
        cacheCalendarDataSource.update(entity.toMapper())
    }

    override suspend fun delete(uid: Int) {
        cacheCalendarDataSource.delete(uid)
    }
}