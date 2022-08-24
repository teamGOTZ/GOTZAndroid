package com.gotz.cache.mapper

import com.gotz.cache.model.CacheCalendarEntity
import com.gotz.data.model.CalendarEntity

object CacheCalendarMapper {

    fun CacheCalendarEntity.toMapper() = CalendarEntity(
        uid = uid,
        title = title,
        content = content,
        isAllDay = isAllDay,
        start = start,
        end = end
    )

    fun CalendarEntity.toMapper() = CacheCalendarEntity(
        uid = uid,
        title = title,
        content = content,
        isAllDay = isAllDay,
        start = start,
        end = end
    )
}