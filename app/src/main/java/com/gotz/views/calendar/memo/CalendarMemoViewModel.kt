package com.gotz.views.calendar.memo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gotz.base.BaseViewModel
import com.gotz.database.calendarmemo.CalendarMemo
import com.gotz.repoository.RoomCalendarMemoRepositoryImpl
import com.gotz.util.EventUtil
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class CalendarMemoViewModel(private val repository: RoomCalendarMemoRepositoryImpl):BaseViewModel() {
    lateinit var uidRoomCalendarMemo: LiveData<CalendarMemo>

    private val _saveButtonEvent = MutableLiveData<EventUtil<Boolean>>()
    val saveButtonEvent : LiveData<EventUtil<Boolean>> get() = _saveButtonEvent

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val isAllDay = MutableLiveData<Boolean>()
    val year = MutableLiveData<Int>()
    val month = MutableLiveData<Int>()
    val day = MutableLiveData<Int>()
    val startTime = MutableLiveData<Int>()
    val endTime = MutableLiveData<Int>()

    val uid = MutableLiveData<Int>(-1)
    /*
    val start = MutableLiveData<Long>()
    val end = MutableLiveData<Long>()
    */
    init{
        title.value = ""
        content.value = ""
        isAllDay.value = false
        year.value = 0
        month.value = 0
        day.value = 0
        startTime.value = 0
        endTime.value = 0
        //delete()
    }

    fun updateData(uid:Int){
        uidRoomCalendarMemo = repository.uidRoomCalendar(uid).asLiveData()
    }

    fun saveData(){
        Log.e("UID", uid.value.toString())
        if(uid.value == -1){
            Log.e("title", title.value.toString())
            Log.e("content", content.value.toString())
            Log.e("isAllDay", isAllDay.value.toString())
            Log.e("year", year.value.toString())
            Log.e("month", month.value.toString())
            Log.e("day", day.value.toString())
            Log.e("startTime", startTime.value.toString())
            Log.e("endTime", endTime.value.toString())
            Log.e("DateTime", DateTime(year.value!!, month.value!!, day.value!!, startTime.value!!/100, startTime.value!!%100, 0).millis.toString())
            Log.e("DateTime", DateTime(year.value!!, month.value!!, day.value!!, endTime.value!!/100, endTime.value!!%100, 0).millis.toString())

            lateinit var calendarMemo: CalendarMemo
            var startVar: Long
            var endVar: Long
            if(isAllDay.value!!){
                startVar = DateTime(year.value!!, month.value!!, day.value!!, 0, 0, 0).millis
                endVar = DateTime(year.value!!, month.value!!, day.value!!, 0, 0, 0).millis
                //calendarMemo = CalendarMemo(0,title.value!!,content.value!!, isAllDay.value!!, startVal, endVal)

            }else{
                startVar = DateTime(year.value!!, month.value!!, day.value!!, startTime.value!!/100, startTime.value!!%100, 1).millis
                endVar = DateTime(year.value!!, month.value!!, day.value!!, endTime.value!!/100, endTime.value!!%100, 1).millis
            }
            calendarMemo = CalendarMemo(0,title.value!!,content.value!!, isAllDay.value!!, startVar, endVar)
            insert(calendarMemo)
        }
        else{
            Log.e("title", title.value.toString())
            Log.e("content", content.value.toString())
            Log.e("isAllDay", isAllDay.value.toString())
            Log.e("year", year.value.toString())
            Log.e("month", month.value.toString())
            Log.e("day", day.value.toString())
            Log.e("startTime", startTime.value.toString())
            Log.e("endTime", endTime.value.toString())
            Log.e("DateTime", DateTime(year.value!!, month.value!!, day.value!!, startTime.value!!/100, startTime.value!!%100, 0).millis.toString())
            Log.e("DateTime", DateTime(year.value!!, month.value!!, day.value!!, endTime.value!!/100, endTime.value!!%100, 0).millis.toString())

            lateinit var calendarMemo: CalendarMemo
            var startVar: Long
            var endVar: Long
            if(isAllDay.value!!){
                startVar = DateTime(year.value!!, month.value!!, day.value!!, 0, 0, 0).millis
                endVar = DateTime(year.value!!, month.value!!, day.value!!, 0, 0, 0).millis
                //calendarMemo = CalendarMemo(0,title.value!!,content.value!!, isAllDay.value!!, startVal, endVal)

            }else{
                startVar = DateTime(year.value!!, month.value!!, day.value!!, startTime.value!!/100, startTime.value!!%100, 1).millis
                endVar = DateTime(year.value!!, month.value!!, day.value!!, endTime.value!!/100, endTime.value!!%100, 1).millis
            }
            calendarMemo = CalendarMemo(uid.value!!,title.value!!,content.value!!, isAllDay.value!!, startVar, endVar)
            update(calendarMemo)
        }


        _saveButtonEvent.value = EventUtil(true)
    }

    fun insert(calendarMemo: CalendarMemo)= viewModelScope.launch {
        repository.insert(calendarMemo)
    }

    fun update(calendarMemo: CalendarMemo)= viewModelScope.launch{
        repository.update(calendarMemo)
    }
}