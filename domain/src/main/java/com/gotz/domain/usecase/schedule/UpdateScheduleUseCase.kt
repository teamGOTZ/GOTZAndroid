package com.gotz.domain.usecase.schedule

import com.gotz.domain.model.Schedule
import com.gotz.domain.repository.schedule.ScheduleRepository

internal typealias UpdateScheduleBaseUseCase = suspend (Schedule) -> (Unit)

class UpdateScheduleUseCase(
    private val scheduleRepository: ScheduleRepository
): UpdateScheduleBaseUseCase {
    override suspend fun invoke(schedule: Schedule) {
        scheduleRepository.updateSchedule(schedule)
    }
}