package com.gotz.views.calendar.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gotz.repoository.RoomCalendarMemoRepositoryImpl
import java.lang.IllegalArgumentException

class CalendarMemoViewModelFactory(private val repository: RoomCalendarMemoRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CalendarMemoViewModel::class.java)){
            return CalendarMemoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}