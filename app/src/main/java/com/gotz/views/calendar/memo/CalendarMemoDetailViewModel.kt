package com.gotz.views.calendar.memo

import androidx.lifecycle.*
import com.gotz.base_legacy.BaseViewModelLegacy
import com.gotz.database.calendarmemo.CalendarMemo
import com.gotz.repoository.RoomCalendarMemoRepositoryImpl
import kotlinx.coroutines.launch

class CalendarMemoDetailViewModel(private val repository: RoomCalendarMemoRepositoryImpl):BaseViewModelLegacy() {
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