package com.gotz.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gotz.data.db.GotzDataBase.Companion.DATE_TABLE

@Entity(
    tableName = DATE_TABLE
)
data class DateEntity(
    @PrimaryKey val dateId: String
)