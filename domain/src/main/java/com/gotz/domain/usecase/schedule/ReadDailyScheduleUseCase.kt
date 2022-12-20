package com.gotz.domain.usecase.schedule

import com.gotz.domain.model.ScheduleWithDate
import com.gotz.domain.repository.schedule.ScheduleRepository
import kotlinx.coroutines.flow.Flow

internal typealias ReadDailyScheduleBaseUseCase = (String) -> Flow<ScheduleWithDate?>

class ReadDailyScheduleUseCase(
    private val scheduleRepository: ScheduleRepository
): ReadDailyScheduleBaseUseCase {
    override fun invoke(date: String): Flow<ScheduleWithDate?> =
        scheduleRepository.readDailySchedule(date)
}