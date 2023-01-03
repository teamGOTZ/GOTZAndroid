package com.gotz.base.custom.calendar

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.ContextThemeWrapper
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.gotz.base.R
import com.gotz.base.util.CalendarUtil.getDateColor
import com.gotz.base.util.CalendarUtil.getDateNotColor
import com.gotz.base.util.CalendarUtil.isSameMonth
import com.gotz.base.util.DimensionUtil.dpToPx
import com.gotz.base.util.StringUtil
import org.joda.time.DateTime

class DayItemLongView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes private val defStyleAttr: Int = R.attr.itemViewStyle,
    @StyleRes private val defStyleRes: Int = R.style.Calendar_ItemViewStyle,
    private val date: DateTime = DateTime(),
    private val firstDayOfMonth: DateTime = DateTime(),
    private val dayText: Int = -1,
    private var scheduleCount: Int = 0
) : View(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {

    private val bounds = Rect()

    private var paint: Paint = Paint()
    private var paintRect: Paint = Paint()
    private var paintText: Paint = Paint()
    private var paintWhite: Paint = Paint()
    private var paintToday: Paint = Paint()
    private var paintFill: Paint = Paint()
    private var paintCircle: Paint = Paint()
    private var paintPlus: Paint = Paint()

    private val strokeW = dpToPx(context,1F)
    private val offset = strokeW/2F

    private var dateForSelect:Boolean = false

    init{
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes){
            val dayTextSize = getDimensionPixelSize(R.styleable.CalendarView_dayTextSize, 0).toFloat()
            /* 흰색 배경에 유색 글씨 */
            paint = TextPaint().apply {
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
                textSize = dayTextSize
                color = getDateColor(date.dayOfWeek)
                if (!isSameMonth(date, firstDayOfMonth)) {
                    color = getDateNotColor(date.dayOfWeek)
                }
            }

            paintRect = Paint().apply {
                textSize = getDimensionPixelSize(R.styleable.CalendarView_scheduleTextSize,0).toFloat()
                color = Color.parseColor("#EEEEEE")
            }

            paintText = Paint().apply {
                isAntiAlias = true
                textSize = dpToPx(context,11F)
                color = Color.parseColor("#424242")
                typeface = Typeface.SANS_SERIF
            }

            paintWhite = Paint().apply {
                color = Color.WHITE
            }

            paintToday = Paint().apply {
                isAntiAlias = true
                color = Color.BLACK
                strokeWidth = strokeW
                style = Paint.Style.STROKE
            }

            paintFill = Paint().apply {
                isAntiAlias = true
                color = Color.parseColor("#EEEEEE")
                strokeWidth = strokeW
                style = Paint.Style.FILL
            }

            paintCircle = Paint().apply {
                isAntiAlias = true
                color = Color.parseColor("#424242")
                style = Paint.Style.FILL
            }

            paintPlus = Paint().apply {
                isAntiAlias = true
                color = Color.parseColor("#757575")
                strokeCap = Paint.Cap.ROUND
                strokeWidth = strokeW
                style = Paint.Style.FILL_AND_STROKE
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(canvas == null) return

        if(dayText == -1){
            drawPosition(canvas)
            drawCalendar(canvas)
            drawToday(canvas)
        }else{
            drawTitle(canvas)
        }
    }

    private fun drawTitle(canvas: Canvas){
        paint.typeface = Typeface.SANS_SERIF
        paint.getTextBounds("일", 0, 1, bounds)
        paint.textSize = dpToPx(context,12F)
        if(dayText == 0){
            paint.color = Color.parseColor("#F44336")
            canvas.drawText(StringUtil.getCharDayOfWeek(7), width/2F, height.toFloat() - dpToPx(context,2F), paint)
        }
        else{
            paint.color = Color.parseColor("#757575")
            canvas.drawText(StringUtil.getCharDayOfWeek(dayText), width/2F, height.toFloat() - dpToPx(context,2F), paint)
        }
    }

    private fun drawToday(canvas: Canvas){
        if(date.year == DateTime.now().year && date.monthOfYear == DateTime.now().monthOfYear && date.dayOfMonth == DateTime.now().dayOfMonth){
            canvas.drawRoundRect(0F + offset,0F + offset, width.toFloat() - offset, height.toFloat() - offset, dpToPx(context,50F), dpToPx(context,50F), paintToday)
        }
    }

    private fun drawPosition(canvas: Canvas){
        if(dateForSelect){
            canvas.drawRoundRect(0F + (offset * 2),0F + (offset * 2), width.toFloat() - (offset * 2), height.toFloat() - (offset * 2), dpToPx(context,50F), dpToPx(context,50F), paintFill)
        }
    }

    private fun drawCalendar(canvas: Canvas){
        val date = date.dayOfMonth.toString()
        val textTop = paint.fontMetrics.leading - paint.fontMetrics.top + dpToPx(context,8F)
        val scheduleTop = textTop + dpToPx(context, 11F)

        paint.typeface = ResourcesCompat.getFont(context, R.font.montserratmedium)
        paint.getTextBounds(date, 0, date.length, bounds)
        canvas.drawText(
            date,
            width / 2F,
            textTop,
            paint
        )

        drawSchedule(canvas, scheduleTop)
    }

    private fun drawSchedule(canvas: Canvas, y: Float){
        val x = width/2F
        val r = 7F
        val d = 20F
        if(scheduleCount != 0) {
            when(scheduleCount){
                1 -> {
                    canvas.drawCircle(x, y, r, paintCircle)
                }
                2 -> {
                    canvas.drawCircle(x- d/2, y, r, paintCircle)
                    canvas.drawCircle(x+ d/2, y, r, paintCircle)
                }
                3 -> {
                    canvas.drawCircle(x, y, r, paintCircle)
                    canvas.drawCircle(x-d, y, r, paintCircle)
                    canvas.drawCircle(x+d, y, r, paintCircle)
                }
                else -> {
                    canvas.drawCircle(x- d - (d/2), y, r, paintCircle)
                    canvas.drawCircle(x- (d/2), y, r, paintCircle)
                    canvas.drawCircle(x+ (d/2), y, r, paintCircle)

                    canvas.drawLine(x+ d + (d/2) - r, y, x+ d + (d/2) + r, y, paintPlus)
                    canvas.drawLine(x+d+(d/2), y-r, x+d+(d/2), y+r, paintPlus)
                }

            }
        }
    }

    fun getDateTime() = date

    fun setDateForSelect(flag: Boolean){
        dateForSelect = flag
    }

    fun setScheduleCount(count: Int) {
        scheduleCount = count
        invalidate()
    }
}