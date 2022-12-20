package com.gotz.domain.repository.schedule

import com.gotz.domain.model.Schedule
import com.gotz.domain.model.ScheduleWithDate
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun createSchedule(schedule: Schedule)

    fun readAllSchedule(): Flow<List<ScheduleWithDate>>

    fun readMonthlySchedule(startDate: String, endDate: String): Flow<List<ScheduleWithDate>>

    fun readDailySchedule(date: String): Flow<ScheduleWithDate?>

    suspend fun updateSchedule(schedule: Schedule)

    suspend fun deleteSchedule(schedule: Schedule)
}