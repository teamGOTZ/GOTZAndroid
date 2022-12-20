package com.gotz.presentation.view.calendar.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gotz.base.BaseViewModel
import org.joda.time.DateTime

class CalendarViewModel : BaseViewModel() {

    companion object {
        const val CALENDAR_NONE = 0
        const val CALENDAR_MONTH = 1
        const val CALENDAR_WEEK = 2

    }

    private val _dateTime = MutableLiveData<DateTime>()
    val dateTime: LiveData<DateTime> get() = _dateTime

    private val _calendarStatus = MutableLiveData<Int>()
    val calendarStatus: LiveData<Int> get() = _calendarStatus

    private val _currentDate = MutableLiveData<String>()
    val currentDate : LiveData<String> get() = _currentDate

    private val _clickStatus = MutableLiveData<Boolean>()
    val clickStatus: LiveData<Boolean> get() = _clickStatus

    init {
        _dateTime.value = DateTime.now()
        _calendarStatus.value = CALENDAR_MONTH
        _currentDate.value = "${_dateTime.value?.year}.${_dateTime.value?.monthOfYear}"
    }

    fun setCalendarStatus(status: Int) {
        _calendarStatus.value = status
    }

    fun setClickStatus(status: Boolean) {
        _clickStatus.value = status
    }

    fun getCalendarStatus(): Int =
        calendarStatus.value?: CALENDAR_NONE

    fun setDateTime(millis: Long) {
        _dateTime.value = DateTime(millis)
        _currentDate.value = "${_dateTime.value?.year}.${_dateTime.value?.monthOfYear}"
    }

    fun getDateTime(): DateTime =
        dateTime.value?:DateTime.now()
}