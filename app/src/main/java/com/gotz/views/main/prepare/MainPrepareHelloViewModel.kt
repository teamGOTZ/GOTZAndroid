package com.gotz.views.main.prepare

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gotz.base_legacy.BaseViewModelLegacy
import com.gotz.database.calendarmemo.CalendarMemo
import com.gotz.repoository.RoomCalendarMemoRepositoryImpl
import com.gotz.util.EventUtil
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class MainPrepareHelloViewModel(private val repository: RoomCalendarMemoRepositoryImpl): BaseViewModelLegacy()  {

    private val _saveButtonEvent = MutableLiveData<EventUtil<Boolean>>()
    val saveButtonEvent : LiveData<EventUtil<Boolean>> get() = _saveButtonEvent
    val str: String = "위딛과 처음 만난 날!\n일정을 등록해보세요:)"

    fun saveData(){
        lateinit var calendarMemo: CalendarMemo
        val dateTime = DateTime(DateTime.now().year, DateTime.now().monthOfYear, DateTime.now().dayOfMonth, 0, 0, 0)
        calendarMemo = CalendarMemo(0,str,"", true, dateTime.millis, dateTime.millis)
        insert(calendarMemo)

        _saveButtonEvent.value = EventUtil(true)
    }

    fun insert(calendarMemo: CalendarMemo)= viewModelScope.launch {
        repository.insert(calendarMemo)
    }
}