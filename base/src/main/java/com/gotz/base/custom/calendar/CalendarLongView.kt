package com.gotz.base.custom.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.gotz.base.R
import com.gotz.base.util.CalendarUtil
import com.gotz.base.util.CalendarUtil.WEEKS_PER_MONTH
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
        val iWidth = (width/ DateTimeConstants.DAYS_PER_WEEK).toFloat()
        val iHeight = ((height - dp2px(36F))/ WEEKS_PER_MONTH).toFloat()

        var index = 0
        children.forEach {
            val left = (index % DateTimeConstants.DAYS_PER_WEEK) * iWidth
            val top = dp2px(36F) + ((index-7) / DateTimeConstants.DAYS_PER_WEEK) * iHeight
            if(index < 7){
                it.layout(left.toInt(), 0, (left+iWidth).toInt(), dp2px(36F).toInt())
            }
            else{
                it.layout(left.toInt(), top.toInt(), (left+iWidth).toInt(), (top+iHeight).toInt())
            }
            index++
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(canvas == null) return

        var x = width/DateTimeConstants.DAYS_PER_WEEK.toFloat()
        var y = dp2px(36F)
        for( idx in 0 until 7){
            canvas.drawText(CalendarUtil.charDayOfWeek(idx).toString(), x*idx, 20F, paint )
        }
    }

    fun initCalendar(firstDayOfMonth: DateTime, list: List<DateTime>){
        for(idx in 0 until 7){
            addView(DayItemLongView(
                context = context,
                dayText = idx
            ))
        }
        list.forEach{
            val start = it.millis
            val end = it.plusDays(1).millis

            addView(DayItemLongView(
                context = context,
                date = it,
                firstDayOfMonth = firstDayOfMonth
            ))
        }
    }

    fun dp2px(dp:Float): Float{
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}