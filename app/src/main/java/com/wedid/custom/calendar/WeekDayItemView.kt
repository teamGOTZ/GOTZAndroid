package com.wedid.custom.calendar

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
import com.wedid.R
import com.wedid.util.CalendarUtil
import org.joda.time.DateTime

class WeekDayItemView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes private val defStyleAttr: Int = R.attr.itemViewStyle,
    @StyleRes private val defStyleRes: Int = R.style.Calendar_ItemViewStyle,
    private val date: DateTime = DateTime(),
    private val dayOfWeek: Int = 0,
    private val count: Int = 0
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

    private val roundCorner = dp2px(51F)


    private val strokeW = dp2px(1F)
    private val offset = strokeW

    private var mDayOfWeek:Int = dayOfWeek
    private var mCount = count

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
                textSize = dp2px(12F)
                color = CalendarUtil.getDateTextColor(date.dayOfWeek)
            }

            paintTextStart = TextPaint().apply {
                textAlign = Paint.Align.CENTER
                textSize = dp2px(11F)
                isAntiAlias = true
                color = Color.BLACK
                pathEffect = CornerPathEffect(roundCorner)
                strokeWidth = dp2px(1F)
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

        drawStartDay(canvas)
        drawPosition(canvas)
        drawCalendar(canvas)
        drawSchedule(canvas)
        drawToday(canvas)

    }

    fun drawStartDay(canvas: Canvas){
        val textTop = paintTextStart.textSize
        if(date.dayOfMonth == 1){
            paintTextStart.style = Paint.Style.FILL
            canvas.drawText(date.monthOfYear .toString() + "월", (width/2).toFloat(), textTop, paintTextStart)

            paintTextStart.style = Paint.Style.STROKE

            val path = Path()
            path.moveTo(width/2F - dp2px(12F), dp2px(13F))
            path.lineTo(width/2F, dp2px(15.5F))
            path.lineTo(width/2F + dp2px(12F), dp2px(13F))
            canvas.drawPath(path, paintTextStart)
        }


    }

    fun drawToday(canvas: Canvas){
        val today = DateTime(DateTime.now().year, DateTime.now().monthOfYear, DateTime.now().dayOfMonth, 0, 0, 0)
        if(date == today){
            val marginX = dp2px(46F)/2F
            val marginY = dp2px(70F)/2F
            val centerX = width/2F
            val centerY = dp2px(51F)
            canvas.drawRoundRect(centerX - marginX + offset, centerY - marginY + offset, centerX + marginX - offset, centerY + marginY - offset, dp2px(50F),dp2px(50F), paintRect)
        }
    }

    fun drawPosition(canvas: Canvas){
        if(date.dayOfWeek == mDayOfWeek){
            val marginX = dp2px(46F)/2F
            val marginY = dp2px(70F)/2F
            val centerX = width/2F
            val centerY = dp2px(51F)
            ////canvas.drawRoundRect(centerX - marginX, centerY - marginY, centerX + marginX, centerY + marginY, dp2px(50F),dp2px(50F), paintRectFill)
            canvas.drawRoundRect(centerX - marginX + offset, centerY - marginY + offset, centerX + marginX - offset, centerY + marginY - offset, dp2px(50F),dp2px(50F), paintRectFill)

        }
    }

    fun drawCalendar(canvas: Canvas){
        val day = date.dayOfMonth.toString()

        canvas.drawText(
            listDay.get(date.dayOfWeek),
            width/2F,
            height/2F - dp2px(5F),
            paintText
        )

        canvas.drawText(
            day,
            width/2F,
            height/2F + dp2px(18F),
            paint
        )
    }

    fun drawSchedule(canvas: Canvas){
        val x = width/2F
        val y = height - dp2px(18F)
        val r = 7F
        val d = 20F
        if(mCount != 0) {
            when(mCount){
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

                    //paintCircle.color = Color.parseColor("#757575")

                    canvas.drawLine(x+ d + (d/2) - r, y, x+ d + (d/2) + r, y, paintPlus)
                    canvas.drawLine(x+d+(d/2), y-r, x+d+(d/2), y+r, paintPlus)
                }

            }
        }
    }

    fun setCount(count:Int){
        mCount = count
    }

    fun setDayOfWeek(day:Int){
        mDayOfWeek = day
    }

    fun getYear() = date.year
    fun getMonth() = date.monthOfYear
    fun getDay() = date.dayOfMonth

    fun dp2px(dp:Float): Float{
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}