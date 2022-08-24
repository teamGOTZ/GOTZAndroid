package com.gotz.domain.model

data class CalendarModel(
    val uid: Int,
    val title: String,
    val content: String,
    val isAllDay: Boolean,
    val start: Long,
    val end: Long
)