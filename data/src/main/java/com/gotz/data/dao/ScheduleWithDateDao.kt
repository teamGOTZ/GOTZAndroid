package com.gotz.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.gotz.data.db.GotzDataBase.Companion.DATE_TABLE
import com.gotz.data.model.ScheduleWithDateEntity

@Dao
interface ScheduleWithDateDao {
    @Transaction
    @Query("SELECT * FROM $DATE_TABLE")
    fun getAllSchedule(): List<ScheduleWithDateEntity>

    @Transaction
    @Query("SELECT * FROM $DATE_TABLE WHERE dateId BETWEEN :startDateId AND :endDateId")
    fun getMonthlySchedule(startDateId: String, endDateId: String): List<ScheduleWithDateEntity>

    @Transaction
    @Query("SELECT * FROM $DATE_TABLE WHERE dateId = :dateId")
    fun getDailySchedule(dateId: String): ScheduleWithDateEntity?
}