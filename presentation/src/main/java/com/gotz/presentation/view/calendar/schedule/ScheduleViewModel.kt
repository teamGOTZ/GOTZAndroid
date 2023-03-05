package com.gotz.presentation.view.calendar.schedule

import androidx.lifecycle.viewModelScope
import com.gotz.presentation.base.BaseViewModel
import com.gotz.domain.model.Schedule
import com.gotz.domain.model.ScheduleWithDate
import com.gotz.domain.usecase.schedule.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class ScheduleViewModel(
    private val createScheduleUseCase: CreateScheduleUseCase,
    private val readAllScheduleUseCase: ReadAllScheduleUseCase,
    private val readMonthlyScheduleUseCase: ReadMonthlyScheduleUseCase,
    private val readDailyScheduleUseCase: ReadDailyScheduleUseCase,
    private val updateScheduleUseCase: UpdateScheduleUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase
) : BaseViewModel() {

    fun createSchedule(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            createScheduleUseCase(schedule)
        }
    }

    fun readDailyScheduleUseCase(date: DateTime): Flow<ScheduleWithDate?> =
        readDailyScheduleUseCase(date.toString("yyyy-MM-dd"))

    fun readMonthlyScheduleUseCase(startDate: DateTime, endDate: DateTime): Flow<List<ScheduleWithDate>> =
        readMonthlyScheduleUseCase(startDate.toString("yyyy-MM-dd"), endDate.toString("yyyy-MM-dd"))

    fun updateSchedule(schedule: Schedule){
        viewModelScope.launch(Dispatchers.IO) {
            updateScheduleUseCase(schedule)
        }
    }

    fun deleteSchedule(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteScheduleUseCase(schedule)
        }
    }
}