package com.gotz.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gotz.data.db.GotzDataBase

@Entity(tableName = GotzDataBase.CALENDAR_TABLE)
data class CalendarEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "isAllDay") val isAllDay: Boolean,
    @ColumnInfo(name = "start") val start: Long,
    @ColumnInfo(name = "end") val end: Long
)