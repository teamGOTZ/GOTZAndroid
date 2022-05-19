package com.wedid.views.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wedid.repoository.RoomCalendarMemoRepositoryImpl
import java.lang.IllegalArgumentException

class CalendarViewModelFactory(private val repository: RoomCalendarMemoRepositoryImpl) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CalendarViewModel::class.java)){
            return CalendarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}