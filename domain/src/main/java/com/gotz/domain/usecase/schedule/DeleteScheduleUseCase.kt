package com.gotz.domain.usecase.schedule

import com.gotz.domain.model.Schedule
import com.gotz.domain.repository.schedule.ScheduleRepository

internal typealias DeleteScheduleBaseUseCase = suspend (Schedule) -> (Unit)

class DeleteScheduleUseCase(
    private val scheduleRepository: ScheduleRepository
) : DeleteScheduleBaseUseCase {
    override suspend fun invoke(schedule: Schedule) {
        scheduleRepository.deleteSchedule(schedule)
    }
}