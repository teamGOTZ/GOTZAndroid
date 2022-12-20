package com.gotz.base.util

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.DateTimeConstants.SUNDAY

object CalendarUtil {

    const val WEEKS_PER_MONTH = 6
    const val DAY_OF_START = 1

    fun getDateTimeForMonthlyCalendar(now: DateTime, position: Int): DateTime {
        return DateTime(now.year, now.monthOfYear, 1, 0, 0, 0).plusMonths(position)
    }

    fun getDateTimeForWeeklyCalendar(now: DateTime, position: Int): DateTime {
        return if(now.dayOfWeek == SUNDAY){
            DateTime(now.year, now.monthOfYear, now.dayOfMonth, 0, 0,0).plusWeeks(position)
        } else {
            DateTime(now.year, now.monthOfYear, now.dayOfMonth, 0, 0,0).plusWeeks(position).minusDays(now.dayOfWeek)
        }
    }

    fun getDateStartOfWeek(dateTime: DateTime): DateTime {
        return dateTime.withDayOfWeek(dateTime.dayOfWeek().minimumValue)
    }

    fun getDateEndOfWeek(dateTime: DateTime): DateTime {
        return dateTime.withDayOfWeek(dateTime.dayOfWeek().maximumValue)
    }

    fun getDateStartOfMonth(dateTime: DateTime): DateTime {
        return dateTime.withDayOfMonth(dateTime.dayOfMonth().minimumValue)
    }

    fun getDateEndOfMonth(dateTime: DateTime): DateTime {
        return dateTime.withDayOfMonth(dateTime.dayOfMonth().maximumValue)
    }

    fun startDayOfWeek(dateTime: DateTime): DateTime {
        if (dateTime.dayOfWeek != 7) return dateTime.minusDays(dateTime.dayOfWeek)
        return dateTime
    }
    /**
     * 선택된 날짜에 해당하는 주간 달력을 반환한다.
     */
    fun getWeekList(dateTime:DateTime): List<DateTime>{
        val list = mutableListOf<DateTime>()
        val date = startDayOfWeek(dateTime)
        val totalDay = DateTimeConstants.DAYS_PER_WEEK

        for(i in 0 until totalDay){
            list.add(DateTime(date.plusDays(i)))
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

        for (i in 0 until totalDay) {
            list.add(DateTime(startValue.plusDays(i)))
        }

        return list
    }

    /**
     * 해당 calendar 의 이전 달의 일 갯수를 반환한다.
     */
    fun getPrevOffSet(dateTime: DateTime): Int {
        var prevMonthTailOffset = dateTime.dayOfWeek

        if (prevMonthTailOffset >= 7) prevMonthTailOffset %= 7

        return prevMonthTailOffset
    }

    fun isSameDay(first: DateTime, second: DateTime): Boolean =
        isSameMonth(first, second) && first.dayOfMonth == second.dayOfMonth

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