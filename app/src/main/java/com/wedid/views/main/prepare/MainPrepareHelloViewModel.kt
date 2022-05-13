package com.wedid.views.main.prepare

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wedid.base.BaseViewModel
import com.wedid.database.calendarmemo.CalendarMemo
import com.wedid.repoository.RoomCalendarMemoRepositoryImpl
import com.wedid.util.EventUtil
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class MainPrepareHelloViewModel(private val repository: RoomCalendarMemoRepositoryImpl): BaseViewModel()  {

    private val _saveButtonEvent = MutableLiveData<EventUtil<Boolean>>()
    val saveButtonEvent : LiveData<EventUtil<Boolean>> get() = _saveButtonEvent
    val str: String = "위딛과 처음 만난 날!\n일정을 등록해보세요:)"

    fun saveData(){
        lateinit var calendarMemo: CalendarMemo
        calendarMemo = CalendarMemo(0,str,"", true, DateTime.now().millis, DateTime.now().millis)
        insert(calendarMemo)

        _saveButtonEvent.value = EventUtil(true)
    }

    fun insert(calendarMemo: CalendarMemo)= viewModelScope.launch {
        repository.insert(calendarMemo)
    }
}