package com.wedid.views.calendar.memo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.wedid.base.BaseViewModel
import com.wedid.database.calendarmemo.CalendarMemo
import com.wedid.repoository.RoomCalendarMemoRepositoryImpl
import kotlinx.coroutines.launch

class CalendarMemoDetailViewModel(private val repository: RoomCalendarMemoRepositoryImpl):BaseViewModel() {
    lateinit var uidRoomCalendarMemo: LiveData<CalendarMemo>

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val isAllDay = MutableLiveData<Boolean>()
    val start = MutableLiveData<Long>()
    val end = MutableLiveData<Long>()

    init {

    }

    fun updateData(uid:Int){
        uidRoomCalendarMemo = repository.uidRoomCalendar(uid).asLiveData()
    }

    fun delete(uid:Int) = viewModelScope.launch {
        repository.delete(uid)
    }
}