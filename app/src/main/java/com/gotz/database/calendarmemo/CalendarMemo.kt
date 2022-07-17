package com.gotz.database.calendarmemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CalendarMemo(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "isAllDay") val isAllDay: Boolean,
    @ColumnInfo(name = "start") val start: Long,
    @ColumnInfo(name = "end") val end: Long
)