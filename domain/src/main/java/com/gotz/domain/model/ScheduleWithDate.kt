package com.gotz.domain.model

data class ScheduleWithDate(
    val date: Date,
    val schedule: List<Schedule>
)
