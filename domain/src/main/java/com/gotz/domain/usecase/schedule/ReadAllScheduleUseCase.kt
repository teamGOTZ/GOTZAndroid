package com.gotz.domain.usecase.schedule

import com.gotz.domain.model.ScheduleWithDate
import com.gotz.domain.repository.schedule.ScheduleRepository
import kotlinx.coroutines.flow.Flow

internal typealias ReadAllScheduleBaseUseCase = () -> Flow<List<ScheduleWithDate>>

class ReadAllScheduleUseCase(
    private val scheduleRepository: ScheduleRepository
): ReadAllScheduleBaseUseCase {
    override fun invoke(): Flow<List<ScheduleWithDate>> =
        scheduleRepository.readAllSchedule()
}

