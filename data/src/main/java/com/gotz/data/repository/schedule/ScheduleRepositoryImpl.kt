package com.gotz.data.repository.schedule

import com.gotz.data.mapper.ScheduleMapper.toMapper
import com.gotz.data.mapper.ScheduleWithDateMapper.toMapper
import com.gotz.data.model.ScheduleWithDateEntity
import com.gotz.data.repository.schedule.local.LocalScheduleDataSource
import com.gotz.domain.model.Schedule
import com.gotz.domain.model.ScheduleWithDate
import com.gotz.domain.repository.schedule.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ScheduleRepositoryImpl(
    private val localScheduleDataSource: LocalScheduleDataSource
): ScheduleRepository {
    override suspend fun createSchedule(schedule: Schedule) {
        localScheduleDataSource.createSchedule(schedule.toMapper())
    }

    override fun readAllSchedule(): Flow<List<ScheduleWithDate>> =
        localScheduleDataSource.readAllSchedule().map {
            it.map{ scheduleWithDateEntity ->
                scheduleWithDateEntity.toMapper()
            }
        }

    override fun readMonthlySchedule(
        startDate: String,
        endDate: String
    ): Flow<List<ScheduleWithDate>> =
        localScheduleDataSource.readMonthlySchedule(startDate, endDate).map {
            it.map { scheduleWithDateEntity ->
                scheduleWithDateEntity.toMapper()
            }
        }

    override fun readDailySchedule(date: String): Flow<ScheduleWithDate?> =
        localScheduleDataSource.readDailySchedule(date).map { scheduleWithDateEntity ->
            scheduleWithDateEntity?.toMapper()
        }


    override suspend fun updateSchedule(schedule: Schedule) {
        localScheduleDataSource.updateSchedule(schedule.toMapper())
    }

    override suspend fun deleteSchedule(schedule: Schedule) {
        localScheduleDataSource.deleteSchedule(schedule.toMapper())
    }
}