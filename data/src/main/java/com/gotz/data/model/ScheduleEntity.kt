package com.gotz.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gotz.data.db.GotzDataBase.Companion.SCHEDULE_TABLE

@Entity(
    tableName = SCHEDULE_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = DateEntity::class,
            parentColumns = arrayOf("dateId"),
            childColumns = arrayOf("dateId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true) val scheduleId: Int = 0,
    val dateId: String,
    val title: String,
    val content: String,
    val isAllDay: Boolean,
    val start: Long,
    val end: Long
)