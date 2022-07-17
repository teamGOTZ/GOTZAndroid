package com.gotz.views.calendar.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gotz.repoository.RoomCalendarMemoRepositoryImpl
import java.lang.IllegalArgumentException

class CalendarMemoDetailViewModelFactory(private val repository: RoomCalendarMemoRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CalendarMemoDetailViewModel::class.java)){
            return CalendarMemoDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}