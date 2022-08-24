package com.gotz.data.mapper

import com.gotz.data.model.CalendarEntity
import com.gotz.domain.model.CalendarModel

object DataCalendarMapper {
    fun CalendarEntity.toMapper() = CalendarModel(
        uid = uid,
        title = title,
        content = content,
        isAllDay = isAllDay,
        start = start,
        end = end
    )

    fun CalendarModel.toMapper() = CalendarEntity(
        uid = uid,
        title = title,
        content = content,
        isAllDay = isAllDay,
        start = start,
        end = end
    )
}