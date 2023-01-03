package com.gotz.base.util

import android.location.Address
import org.joda.time.DateTime

object StringUtil {

    fun getUpdateAt(now: DateTime): String =
        "${now.toString("hh:mm")} ${getAmPm(now)}"

    fun getAddressString(address: Address): String{
        return if(address.locality == null) address.adminArea
        else if(address.adminArea == null) address.locality
        else "${address.adminArea} ${address.locality}"
    }

    fun getBaseDate(now: DateTime): String =
        now.toString("yyyyMMdd")

    fun getBaseTime(now: DateTime): String =
        now.toString("hhmm")

    fun getStrDate(year: Int, month: Int, day: Int): String{
        return "${year}년 ${month}월 ${day}일 (${getCharDayOfWeek(DateTime(year, month, day, 0, 0, 0).dayOfWeek)})"
    }

    fun getStrDate(dateTime: DateTime): String{
        return "${dateTime.year}년 ${dateTime.monthOfYear}월 ${dateTime.dayOfMonth}일 (${getCharDayOfWeek(dateTime.dayOfWeek)})"
    }

    fun getCharDayOfWeek(day: Int): String{
        val list = listOf("", "월", "화", "수", "목", "금", "토", "일")
        return list[day]
    }

    fun getStrTime(hour: Int, minute: Int): String {
        val sb = StringBuilder()

        if (hour > 11) {
            sb.append("오후 ")
            sb.append(
                if (hour == 12) "$hour:"
                else "${hour % 12}:"
            )

            sb.append(
                if (minute < 10) "0$minute"
                else "$minute"
            )
        } else {
            sb.append("오전 ")
            sb.append(
                if (hour == 0) "12:"
                else "$hour:"
            )

            sb.append(
                if (minute < 10) "0$minute"
                else "$minute"
            )
        }

        return sb.toString()
    }

    fun getStrTime(dateTime: DateTime): String{
        val sb = StringBuilder()

        val hour = dateTime.hourOfDay
        val minute = dateTime.minuteOfHour

        if (hour > 11) {
            sb.append("오후 ")
            sb.append(
                if (hour == 12) "$hour:"
                else "${hour % 12}:"
            )

            sb.append(
                if (minute < 10) "0$minute"
                else "$minute"
            )
        } else {
            sb.append("오전 ")
            sb.append(
                if (hour == 0) "12:"
                else "$hour:"
            )

            sb.append(
                if (minute < 10) "0$minute"
                else "$minute"
            )
        }

        return sb.toString()
    }

    fun getStrTime2(dateTime: DateTime): String{
        val sb = StringBuilder()

        val hour = dateTime.hourOfDay
        val minute = dateTime.minuteOfHour

        if (hour > 11) {
            sb.append(
                if (hour == 12) "$hour:"
                else if( hour % 12 < 10 ) "0${hour % 12}:"
                else "${hour % 12}:"
            )

            sb.append(
                if (minute < 10) "0$minute"
                else "$minute"
            )
        } else {
            sb.append(
                if (hour == 0) "12:"
                else if( hour < 10 ) "0$hour:"
                else "$hour:"
            )

            sb.append(
                if (minute < 10) "0$minute"
                else "$minute"
            )
        }

        return sb.toString()
    }

    fun getAmPm(dateTime: DateTime): String{
        val hour = dateTime.hourOfDay

        return if (hour > 11) {
            "pm"
        } else {
            "am"
        }
    }
}