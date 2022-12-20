package com.gotz.domain.usecase.schedule

import com.gotz.domain.model.Schedule
import com.gotz.domain.repository.schedule.ScheduleRepository

internal typealias CreateScheduleBaseUseCase = suspend (Schedule) -> (Unit)

class CreateScheduleUseCase(
    private val scheduleRepository: ScheduleRepository
): CreateScheduleBaseUseCase {
    override suspend fun invoke(schedule: Schedule) {
        scheduleRepository.createSchedule(schedule)
    }
}