package com.gotz.domain.usecase.schedule

import com.gotz.domain.model.ScheduleWithDate
import com.gotz.domain.repository.schedule.ScheduleRepository
import kotlinx.coroutines.flow.Flow

internal typealias ReadMonthlyScheduleBaseUseCase = (String, String) -> Flow<List<ScheduleWithDate>>

class ReadMonthlyScheduleUseCase(
    private val scheduleRepository: ScheduleRepository
): ReadMonthlyScheduleBaseUseCase {
    override fun invoke(startDate: String, endDate: String): Flow<List<ScheduleWithDate>>  =
        scheduleRepository.readMonthlySchedule(startDate, endDate)
}