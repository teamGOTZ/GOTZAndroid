package com.wedid.custom.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.wedid.R
import com.wedid.database.calendarmemo.CalendarMemo
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants

class WeekCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.calendarViewStyle,
    @StyleRes defStyleRes: Int = R.style.Calendar_CalendarViewStyle
) : ViewGroup(ContextThemeWrapper(context,defStyleRes), attrs, defStyleAttr) {


    init{
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes){

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
            getDefaultSize(suggestedMinimumHeight, heightMeasureSpec))
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        val iWidth = (width/ DateTimeConstants.DAYS_PER_WEEK).toFloat()
        val iHeight = height

        var index = 0
        children.forEach {
            val left = index * iWidth
            val top = 0

            //it.setBackgroundColor(Color.BLACK)
            it.layout(left.toInt(), top.toInt(), (left+iWidth).toInt(), (top+iHeight).toInt())
            index++
        }
    }

    fun initCalendar(firstDayOfWeek: DateTime, list: List<DateTime>, dayOfWeek: Int, _listCalendarMemo: List<CalendarMemo>){
        list.forEach{
            var count:Int = 0
            val start = it.millis
            val end = it.plusDays(1).millis

            _listCalendarMemo.forEach {
                if(it.start >= start && it.start < end) count++
            }
            addView(WeekDayItemView(
                context = context,
                date = it,
                dayOfWeek = dayOfWeek,
                count = count
            ))
        }
    }

}
