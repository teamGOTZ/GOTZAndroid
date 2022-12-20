package com.gotz.base.custom.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.gotz.base.R
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants

class CalendarShortView @JvmOverloads constructor(
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
        val iWidth = (width/ DateTimeConstants.DAYS_PER_WEEK)
        val iHeight = height

        var index = 0
        children.forEach {
            val left = index * iWidth
            val top = 0

            //it.setBackgroundColor(Color.BLACK)
            it.layout(left, top, left+iWidth, top+iHeight)
            index++
        }
    }

    fun initCalendar(firstDayOfWeek: DateTime, list: List<DateTime>, dayOfWeek: Int){
        list.forEach{
            var count:Int = 0
            val start = it.millis
            val end = it.plusDays(1).millis

            addView(DayItemShortView(
                context = context,
                date = it,
                dayOfWeek = dayOfWeek
            ))
        }
    }

    fun updateCalendar(scheduleCountList: List<Int>) {
        children.forEachIndexed { index, childView -> childView as DayItemShortView
            childView.setScheduleCount(scheduleCountList[index])
        }
    }

}
