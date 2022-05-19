package com.wedid.views.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.wedid.base.BaseViewModel
import com.wedid.custom.calendar.CalendarLongView
import com.wedid.custom.calendar.CalendarShortView
import com.wedid.database.calendarmemo.CalendarMemo
import com.wedid.repoository.RoomCalendarMemoRepositoryImpl
import org.joda.time.DateTime

class CalendarViewModel(private val repository: RoomCalendarMemoRepositoryImpl):BaseViewModel() {
    val calendarMemo: LiveData<List<CalendarMemo>> = repository.allRoomCalendar.asLiveData()
    // true -> Long 아이템 선택 되었을 때
    // false -> Long 아이템 선택 안되었을 때

    val year = MutableLiveData<Int>()
    val month = MutableLiveData<Int>()
    val day = MutableLiveData<Int>()

    lateinit var calendarLongView: CalendarLongView
    lateinit var calendarShortView: CalendarShortView

    val flagForLongPosition = MutableLiveData<Boolean>()
    val flagForShortPosition = MutableLiveData<Boolean>()
    val flagForLongUpdate = MutableLiveData<Boolean>()
    val flagForShortUpdate = MutableLiveData<Boolean>()
    val flagForSelected = MutableLiveData<Boolean>()
    val flagForSelectedItem = MutableLiveData<Boolean>()
    val flagForList = MutableLiveData<Boolean>()


    init{
        year.value = DateTime.now().year
        month.value = DateTime.now().monthOfYear
        day.value = DateTime.now().dayOfMonth

        flagForLongPosition.value = false
        flagForShortPosition.value = false
        flagForLongUpdate.value = false
        flagForShortUpdate.value = false
        flagForSelected.value = false
        flagForSelectedItem.value = true
        flagForList.value = false


    }
}