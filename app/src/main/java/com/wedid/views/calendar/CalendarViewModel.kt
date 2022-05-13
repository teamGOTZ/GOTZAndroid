package com.wedid.views.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.wedid.base.BaseViewModel
import com.wedid.database.calendarmemo.CalendarMemo
import com.wedid.repoository.RoomCalendarMemoRepositoryImpl
import org.joda.time.DateTime

class CalendarViewModel(private val repository: RoomCalendarMemoRepositoryImpl):BaseViewModel() {
    val calendarMemo: LiveData<List<CalendarMemo>> = repository.allRoomCalendar.asLiveData()
    // true -> Long 아이템 선택 되었을 때
    // false -> Long 아이템 선택 안되었을 때

    val flagForShort = MutableLiveData<Boolean>()
    val flagForSelect = MutableLiveData<Boolean>()

    val year = MutableLiveData<Int>()
    val month = MutableLiveData<Int>()
    val day = MutableLiveData<Int>()


    init{

        year.value = DateTime.now().year
        month.value = DateTime.now().monthOfYear
        day.value = DateTime.now().dayOfMonth

        flagForShort.value = false
        flagForSelect.value = false
    }
}