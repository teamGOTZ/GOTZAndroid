package com.gotz.data.dao

import androidx.room.*
import com.gotz.data.db.GotzDataBase.Companion.SCHEDULE_TABLE
import com.gotz.data.model.ScheduleEntity

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(schedule: ScheduleEntity)

    @Delete
    fun deleteSchedule(schedule: ScheduleEntity)

    @Update
    fun updateSchedule(schedule: ScheduleEntity)

    @Query("SELECT * FROM $SCHEDULE_TABLE ORDER BY start ASC")
    fun getSchedules(): List<ScheduleEntity>

    @Query("DELETE FROM $SCHEDULE_TABLE")
    fun clearSchedules()
}