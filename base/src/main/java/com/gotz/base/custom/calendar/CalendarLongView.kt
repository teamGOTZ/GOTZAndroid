package com.gotz.base.custom.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.gotz.base.R
import com.gotz.base.util.CalendarUtil.WEEKS_PER_MONTH
import com.gotz.base.util.DimensionUtil.dpToPx
import com.gotz.base.util.StringUtil
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants

class CalendarLongView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.calendarViewStyle,
    @StyleRes defStyleRes: Int = R.style.Calendar_CalendarViewStyle
) : ViewGroup(ContextThemeWrapper(context,defStyleRes), attrs, defStyleAttr) {
    private var paint: Paint = Paint()

    init{
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes){
            paint = TextPaint().apply {
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
            getDefaultSize(suggestedMinimumHeight, heightMeasureSpec))
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        val iWidth = (width/ DateTimeConstants.DAYS_PER_WEEK)
        val iHeight = ((height - dpToPx(context,54F))/ WEEKS_PER_MONTH)

        var index = 0
        children.forEach {
            val left = (index % DateTimeConstants.DAYS_PER_WEEK) * iWidth
            val top = dpToPx(context,54F) + ((index-7) / DateTimeConstants.DAYS_PER_WEEK) * iHeight
            if(index < 7){
                it.layout(left, 0, (left+iWidth), dpToPx(context,54F).toInt())
            }
            else{
                it.layout(left.toInt(), top.toInt(), (left+iWidth).toInt(), (top+iHeight).toInt())
            }
            index++
        }
    }

    fun initCalendar(firstDayOfMonth: DateTime, list: List<DateTime>){
        for(idx in 0 until 7){
            addView(DayItemLongView(
                context = context,
                dayText = idx
            ))
        }
        list.forEachIndexed { index, dateTime ->
            val start = dateTime.millis
            val end = dateTime.plusDays(1).millis

            addView(DayItemLongView(
                context = context,
                date = dateTime,
                firstDayOfMonth = firstDayOfMonth
            ))
        }
    }

    fun updateCalendar(scheduleCountList: List<Int>) {
        children.forEachIndexed { index, childView -> childView as DayItemLongView
            if(index < 7) return@forEachIndexed
            childView.setScheduleCount(scheduleCountList[index-7])
        }
    }

}