package com.gotz.data.repository.schedule.local

import com.gotz.data.dao.DateDao
import com.gotz.data.dao.ScheduleDao
import com.gotz.data.dao.ScheduleWithDateDao
import com.gotz.data.model.DateEntity
import com.gotz.data.model.ScheduleEntity
import com.gotz.data.model.ScheduleWithDateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalScheduleDataSourceImpl(
    private val scheduleDao: ScheduleDao,
    private val dateDao: DateDao,
    private val scheduleWithDateDao: ScheduleWithDateDao
) : LocalScheduleDataSource {
    override suspend fun createSchedule(schedule: ScheduleEntity) {
        dateDao.insertDate(DateEntity(schedule.dateId))
        scheduleDao.insertSchedule(schedule)
    }

    override fun readAllSchedule(): Flow<List<ScheduleWithDateEntity>> =
        flow {
            emit(scheduleWithDateDao.getAllSchedule())
        }

    override fun readMonthlySchedule(
        startDate: String,
        endDate: String
    ): Flow<List<ScheduleWithDateEntity>> =
        flow {
            emit(scheduleWithDateDao.getMonthlySchedule(startDate, endDate))
        }

    override fun readDailySchedule(date: String): Flow<ScheduleWithDateEntity?> =
        flow {
            emit(scheduleWithDateDao.getDailySchedule(date))
        }

    override suspend fun updateSchedule(schedule: ScheduleEntity) {
        scheduleDao.updateSchedule(schedule)
    }

    override suspend fun deleteSchedule(schedule: ScheduleEntity) {
        scheduleDao.deleteSchedule(schedule)
    }

}