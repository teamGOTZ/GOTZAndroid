package com.gotz.data.mapper

import com.gotz.data.mapper.DateMapper.toMapper
import com.gotz.data.mapper.ScheduleMapper.toMapper
import com.gotz.data.model.ScheduleEntity
import com.gotz.data.model.ScheduleWithDateEntity
import com.gotz.domain.model.ScheduleWithDate

object ScheduleWithDateMapper {
    fun ScheduleWithDateEntity.toMapper(): ScheduleWithDate {
        val comparator :Comparator<ScheduleEntity> = compareBy{ !it.isAllDay }
        return ScheduleWithDate(
            date.toMapper(),
            schedule.sortedWith(comparator)
            .map
            { scheduleEntity ->
                scheduleEntity.toMapper()
            }
        )
    }

    fun ScheduleWithDate.toMapper() =
        ScheduleWithDateEntity(
            date.toMapper(),
            schedule.map { schedule ->
                schedule.toMapper()
            }
        )

}