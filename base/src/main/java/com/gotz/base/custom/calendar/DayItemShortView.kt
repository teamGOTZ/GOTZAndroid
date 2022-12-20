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
import com.gotz.base.util.CalendarUtil
import com.gotz.base.util.DimensionUtil.dpToPx
import org.joda.time.DateTime

class DayItemShortView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes private val defStyleAttr: Int = R.attr.itemViewStyle,
    @StyleRes private val defStyleRes: Int = R.style.Calendar_ItemViewStyle,
    private val date: DateTime = DateTime(),
    private val dayOfWeek: Int = 0,
    private var scheduleCount: Int = 0
) : View(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {
    private val listDay: List<String> = listOf(" ","월", "화", "수", "목", "금", "토", "일" )
    //private val bounds = Rect()

    private var paint: Paint = Paint()
    private var paintText: Paint = Paint()
    private var paintTextStart: Paint = Paint()
    private var paintRect: Paint = Paint()
    private var paintRectFill: Paint = Paint()
    private var paintCircle: Paint = Paint()
    private var paintPlus: Paint = Paint()

    private val roundCorner = dpToPx(context,51F)

    private val strokeW = dpToPx(context,1F)
    private val offset = strokeW + dpToPx(context, 1F)

    private var dateForSelect:Int = dayOfWeek

    init{
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes){
            val dayTextSize = getDimensionPixelSize(R.styleable.CalendarView_dayTextSize, 0).toFloat()

            /* 흰색 배경에 유색 글씨 */
            paint = TextPaint().apply {
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
                textSize = dayTextSize
                color = CalendarUtil.getDateColor(date.dayOfWeek)
                typeface = ResourcesCompat.getFont(context, R.font.montserratmedium)
            }

            paintText = TextPaint().apply {
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
                textSize = dpToPx(context,12F)
                color = CalendarUtil.getDateTextColor(date.dayOfWeek)
            }

            paintTextStart = TextPaint().apply {
                textAlign = Paint.Align.CENTER
                textSize = dpToPx(context,11F)
                isAntiAlias = true
                color = Color.BLACK
                pathEffect = CornerPathEffect(roundCorner)
                strokeWidth = dpToPx(context,1F)
                typeface = ResourcesCompat.getFont(context, R.font.montserratmedium)
            }

            paintRect = Paint().apply {
                textSize = getDimensionPixelSize(R.styleable.CalendarView_scheduleTextSize,0).toFloat()
                color = Color.BLACK
                style = Paint.Style.STROKE
                strokeWidth = strokeW
                strokeCap = Paint.Cap.ROUND
                isAntiAlias = true
            }

            paintRectFill = Paint().apply {
                color = Color.parseColor("#EEEEEE")
                style = Paint.Style.FILL
                strokeWidth = strokeW
                strokeCap = Paint.Cap.ROUND
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

        drawPosition(canvas)
        drawCalendar(canvas)
        drawToday(canvas)
    }

    private fun drawToday(canvas: Canvas){
        val today = DateTime(DateTime.now().year, DateTime.now().monthOfYear, DateTime.now().dayOfMonth, 0, 0, 0)
        if(date == today){
            val marginX = dpToPx(context,48F)/2F
            val marginY = dpToPx(context,80F)/2F
            val centerX = width/2F
            val centerY = (height/2F) + dpToPx(context, 2F)
            canvas.drawRoundRect(centerX - marginX + offset, centerY - marginY + offset, centerX + marginX - offset, centerY + marginY - offset, dpToPx(context,50F),dpToPx(context,50F), paintRect)
        }
    }

    private fun drawPosition(canvas: Canvas){
        if(date.dayOfWeek == dateForSelect){
            val marginX = dpToPx(context,48F)/2F
            val marginY = dpToPx(context,80F)/2F
            val centerX = width/2F
            val centerY = (height/2F) + dpToPx(context, 2F)
            ////canvas.drawRoundRect(centerX - marginX, centerY - marginY, centerX + marginX, centerY + marginY, dpToPx(50F),dpToPx(50F), paintRectFill)
            canvas.drawRoundRect(centerX - marginX + offset, centerY - marginY + offset, centerX + marginX - offset, centerY + marginY - offset, dpToPx(context,50F),dpToPx(context,50F), paintRectFill)

        }
    }

    private fun drawCalendar(canvas: Canvas){
        val day = date.dayOfMonth.toString()

        canvas.run{
            drawText(
                listDay[date.dayOfWeek],
                width/2F,
                dpToPx(context, 54F) - dpToPx(context, 2F),
                paintText
            )

            drawText(
                day,
                width/2F,
                dpToPx(context, 78F),
                paint
            )
        }

        drawSchedule(canvas, dpToPx(context, 78F) + dpToPx(context, 11F))
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

    fun setDayOfWeek(day:Int){
        dateForSelect = day
    }

    fun setScheduleCount(count: Int) {
        scheduleCount = count
        invalidate()
    }
}