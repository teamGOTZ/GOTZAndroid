package com.gotz.base.util

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants

object CalendarUtil {

    const val WEEKS_PER_MONTH = 6
    const val DAY_OF_START = 1

    fun getStrDate(dateTime: DateTime): String{
        var str: String = ""
        val year = dateTime.year
        val month = dateTime.monthOfYear
        val day = dateTime.dayOfMonth
        str += year.toString() + "년 " + month.toString() + "월 " + day.toString() + "일 (" + charDayOfWeek(dateTime.dayOfWeek) + ")"

        return str
    }

    fun getStrTime(dateTime: DateTime): String{
        var str: String = ""
        val hour = dateTime.hourOfDay
        val minute = dateTime.minuteOfHour

        if(hour > 11){
            str += "오후 "
            if(hour == 12) str += hour.toString() + ":"
            else str += (hour%12).toString() + ":"

            if(minute < 10) str += "0" + minute.toString()
            else str += minute.toString()
        }
        else{
            str += "오전 "
            if(hour == 0) str += "12:"
            else str += hour.toString() + ":"

            if(minute < 10) str += "0" + minute.toString()
            else str += minute.toString()
        }

        return str
    }

    fun charDayOfWeek(day: Int): String{
        val list = listOf<String>("", "월", "화", "수", "목", "금", "토", "일")
        return list.get(day)
    }

    fun startDayOfWeek(dateTime: DateTime): DateTime{
        var date = dateTime
        if(dateTime.dayOfWeek != 7) return date.minusDays(date.dayOfWeek)
        return date
    }
    /**
     * 선택된 날짜에 해당하는 주간 달력을 반환한다.
     */
    fun getWeekList(dateTime:DateTime): List<DateTime>{
        val list = mutableListOf<DateTime>()

        var date = startDayOfWeek(dateTime)

        val startValue = date

        val totalDay = DateTimeConstants.DAYS_PER_WEEK

        for(i in 0 until totalDay){
            list.add(DateTime(startValue.plusDays(i)))
        }

        return list
    }

    /**
     * 선택된 날짜에 해당하는 월간 달력을 반환한다.
     */
    fun getMonthList(dateTime: DateTime): List<DateTime> {
        val list = mutableListOf<DateTime>()

        val date = dateTime.withDayOfMonth(1)
        val prev = getPrevOffSet(date)

        val startValue = date.minusDays(prev)

        val totalDay = DateTimeConstants.DAYS_PER_WEEK * WEEKS_PER_MONTH
        /*
        Log.e("Utils::date", date.toString())
        Log.e("Utils::prev", prev.toString())
        Log.e("Utils::startValue", startValue.toString())
        Log.e("Utils::totlaDay", totalDay.toString())
        */
        for (i in 0 until totalDay) {
            list.add(DateTime(startValue.plusDays(i)))
        }

        return list
    }

    /**
     * 해당 calendar 의 이전 달의 일 갯수를 반환한다.
     */
    private fun getPrevOffSet(dateTime: DateTime): Int {
        var prevMonthTailOffset = dateTime.dayOfWeek

        if (prevMonthTailOffset >= 7) prevMonthTailOffset %= 7

        return prevMonthTailOffset
    }

    /**
     * 같은 달인지 체크
     */
    fun isSameMonth(first: DateTime, second: DateTime): Boolean =
        first.year == second.year && first.monthOfYear == second.monthOfYear

    /**
     * 해당 요일의 색깔을 반환한다.
     * 일요일 -> 빨간색
     * 토요일 -> 파란색
     * 나머지 -> 검정색
     */
    @ColorInt
    fun getDateColor(@IntRange(from=1, to=7) dayOfWeek: Int): Int {
        return when (dayOfWeek) {
            /* 일요일 빨간색 */
            DateTimeConstants.SUNDAY -> Color.parseColor("#F44336")
            /* 그 외 검정색 */
            else -> Color.parseColor("#212121")
        }
    }

    @ColorInt
    fun getDateNotColor(@IntRange(from=1, to=7) dayOfWeek: Int): Int {
        return when (dayOfWeek) {
            /* 일요일 빨간색 */
            DateTimeConstants.SUNDAY -> Color.parseColor("#EF9A9A")
            /* 그 외 검정색 */
            else -> Color.parseColor("#BDBDBD")
        }
    }

    @ColorInt
    fun getDateTextColor(@IntRange(from=1, to=7) dayOfWeek: Int): Int {
        return when (dayOfWeek) {
            /* 일요일 빨간색 */
            DateTimeConstants.SUNDAY -> Color.parseColor("#F44336")
            /* 그 외 검정색 */
            else -> Color.parseColor("#757575")
        }
    }

}