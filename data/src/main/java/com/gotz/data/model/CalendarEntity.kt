package com.gotz.data.model

data class CalendarEntity(
    val uid: Int,
    val title: String,
    val content: String,
    val isAllDay: Boolean,
    val start: Long,
    val end: Long
)