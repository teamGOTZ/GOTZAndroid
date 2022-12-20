package com.gotz.data.repository.schedule.local

import com.gotz.data.model.ScheduleEntity
import com.gotz.data.model.ScheduleWithDateEntity
import kotlinx.coroutines.flow.Flow

interface LocalScheduleDataSource {
    suspend fun createSchedule(schedule: ScheduleEntity)

    fun readAllSchedule(): Flow<List<ScheduleWithDateEntity>>

    fun readMonthlySchedule(startDate: String, endDate: String): Flow<List<ScheduleWithDateEntity>>

    fun readDailySchedule(date: String): Flow<ScheduleWithDateEntity?>

    suspend fun updateSchedule(schedule: ScheduleEntity)

    suspend fun deleteSchedule(schedule: ScheduleEntity)
}