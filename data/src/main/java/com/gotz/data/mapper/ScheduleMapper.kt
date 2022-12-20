package com.gotz.data.mapper

import com.gotz.data.model.ScheduleEntity
import com.gotz.domain.model.Schedule

object ScheduleMapper {
    fun ScheduleEntity.toMapper() = Schedule(
        scheduleId = scheduleId,
        dateId = dateId,
        title = title,
        content = content,
        isAllDay = isAllDay,
        start = start,
        end = end
    )

    fun Schedule.toMapper() = ScheduleEntity(
        scheduleId = scheduleId,
        dateId = dateId,
        title = title,
        content = content,
        isAllDay = isAllDay,
        start = start,
        end = end
    )
}