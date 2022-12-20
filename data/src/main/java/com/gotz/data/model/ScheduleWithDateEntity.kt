package com.gotz.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation

data class ScheduleWithDateEntity(
    @Embedded val date: DateEntity,
    @Relation(
        parentColumn = "dateId",
        entityColumn = "dateId"
    )
    val schedule: List<ScheduleEntity>
)