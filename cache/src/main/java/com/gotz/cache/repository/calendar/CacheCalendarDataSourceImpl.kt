package com.gotz.cache.repository.calendar

import com.gotz.cache.dao.CalendarDao
import com.gotz.cache.mapper.CacheCalendarMapper.toMapper
import com.gotz.data.model.CalendarEntity
import com.gotz.data.source.calendar.CacheCalendarDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CacheCalendarDataSourceImpl(
    private val calendarDao: CalendarDao
): CacheCalendarDataSource{

    override fun readAll(): Flow<List<CalendarEntity>> =
        calendarDao.readAll().map { list ->
            val mutableList = mutableListOf<CalendarEntity>()
            list.map { cacheCalendarEntity ->
                mutableList.add(cacheCalendarEntity.toMapper())
            }
            mutableList
        }

    override fun readByUid(uid: Int): Flow<CalendarEntity> =
        calendarDao.readByUid(uid).map { cacheCalendarEntity ->
            cacheCalendarEntity.toMapper()
        }

    override suspend fun create(entity: CalendarEntity) {
        calendarDao.create(entity.toMapper())
    }

    override suspend fun delete(uid: Int) {
        calendarDao.deleteByUid(uid)
    }

    override suspend fun update(entity: CalendarEntity) {
        calendarDao.updateByUid(entity.title, entity.content, entity.isAllDay, entity.start, entity.end, entity.uid)
    }
}