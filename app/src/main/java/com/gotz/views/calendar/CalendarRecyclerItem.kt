package com.gotz.views.calendar

data class CalendarRecyclerItem(
    val idx: Int = -1,
    val title: String = "",
    val content: String = "",
    val isAllDay: Boolean = false,
    val start: Long = 0L,
    val end: Long = 0L,
    val uid: Int = 0
)