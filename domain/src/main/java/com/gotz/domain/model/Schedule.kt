package com.gotz.domain.model

import java.io.Serializable

data class Schedule(
    val scheduleId: Int = 0,
    val dateId: String,
    val title: String,
    val content: String,
    val isAllDay: Boolean,
    val start: Long,
    val end: Long
) : Serializable {
    companion object {
        fun makeSchedule(
            scheduleId: Int = 0,
            dateId: String,
            title: String,
            content: String,
            isAllDay: Boolean,
            start: Long,
            end: Long
        ): Schedule =
            Schedule(
                scheduleId = scheduleId,
                dateId = dateId,
                title = title,
                content = content,
                isAllDay = isAllDay,
                start = start,
                end = end
            )
    }
}
