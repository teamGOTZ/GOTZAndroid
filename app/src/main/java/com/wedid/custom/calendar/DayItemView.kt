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
import com.wedid.database.calendarmemo.CalendarMemo
import com.wedid.util.CalendarUtil
import com.wedid.util.CalendarUtil.Companion.getDateColor
import com.wedid.util.CalendarUtil.Companion.getDateNotColor
import com.wedid.util.CalendarUtil.Companion.isSameMonth
import org.joda.time.DateTime

class DayItemView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes private val defStyleAttr: Int = R.attr.itemViewStyle,
    @StyleRes private val defStyleRes: Int = R.style.Calendar_ItemViewStyle,
    private val date: DateTime = DateTime(),
    private val firstDayOfMonth: DateTime = DateTime(),
    private val listCalendarMemo: List<CalendarMemo> = ArrayList<CalendarMemo>(),
    private val dayText:Int = -1
) : View(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {

    val str:String = date.monthOfYear.toString() + '/' + date.dayOfMonth

    private val bounds = Rect()

    private var paint: Paint = Paint()
    private var paintRect: Paint = Paint()
    private var paintText: Paint = Paint()
    private var paintWhite: Paint = Paint()
    private var paintToday: Paint = Paint()
    private var paintFill: Paint = Paint()

    private val strokeW = dp2px(1F)
    private val offset = strokeW/2F

    private var mList = listCalendarMemo
    private var mDateForSelect:Boolean = false

    init{
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes){
            val dayTextSize = getDimensionPixelSize(R.styleable.CalendarView_dayTextSize, 0).toFloat()
            val calendarTextSize = getDimensionPixelSize(R.styleable.CalendarView_calendarTextSize,0).toFloat()
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
                //alpha = 50
            }
            paintText = Paint().apply {
                isAntiAlias = true
                textSize = dp2px(11F)
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
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(canvas == null) return

        if(dayText == -1){
            drawPosition(canvas)
            drawCalendar(canvas)
            drawSchedule(canvas)
            drawToday(canvas)
        }else{
            drawTitle(canvas)
        }
    }

    fun drawTitle(canvas: Canvas){
        paint.typeface = Typeface.SANS_SERIF
        paint.getTextBounds("일", 0, 1, bounds)
        paint.textSize = dp2px(12F)
        if(dayText == 0){
            paint.color = Color.parseColor("#F44336")
            canvas.drawText(CalendarUtil.charDayOfWeek(7), width/2F, height.toFloat() - dp2px(4F), paint)
        }
        else{
            paint.color = Color.parseColor("#757575")
            canvas.drawText(CalendarUtil.charDayOfWeek(dayText), width/2F, height.toFloat() - dp2px(4F), paint)
        }
    }

    fun drawToday(canvas: Canvas){
        if(date.year == DateTime.now().year && date.monthOfYear == DateTime.now().monthOfYear && date.dayOfMonth == DateTime.now().dayOfMonth){
            canvas.drawRoundRect(0F + offset,0F + offset, width.toFloat() - offset, height.toFloat() - offset, dp2px(2F), dp2px(2F), paintToday)
        }
    }

    fun drawPosition(canvas: Canvas){
        if(mDateForSelect){
            canvas.drawRoundRect(0F + (offset * 2),0F + (offset * 2), width.toFloat() - (offset * 2), height.toFloat() - (offset * 2), dp2px(2F), dp2px(2F), paintFill)
        }
    }

    fun drawCalendar(canvas: Canvas){
        val date = date.dayOfMonth.toString()
        val textTop = paint.fontMetrics.leading - paint.fontMetrics.top
        paint.typeface = ResourcesCompat.getFont(context, R.font.montserratmedium)
        paint.getTextBounds(date, 0, date.length, bounds)
        canvas.drawText(
            date,
            width / 2F,
            textTop + dp2px(4F),
            paint
        )
    }

    fun drawSchedule(canvas: Canvas){
        var size = mList.size
        var plus:Int = 0
        val left = dp2px(2F)
        val top = dp2px(24F)
        val right = width - left
        val bottom = dp2px(38F)
        val gap = dp2px(15F)
        //Log.e("DRAW", size.toString())
        if(size >= 3){
            plus = size-3
            size = 3
            //Log.e("PLUS", plus.toString())
        }
        for(idx in 0 until size){
            canvas.drawRoundRect(left, top + idx*gap, right, bottom + idx*gap, 1F,1F,paintRect)
            canvas.drawText(mList.get(idx).title, left+dp2px(4F), (bottom + idx*gap) - dp2px(3F), paintText)
            canvas.drawRect(right, top + idx*gap, width.toFloat(), bottom + idx*gap, paintWhite)
        }

        if(plus != 0){
            canvas.drawText("+" + plus.toString(), left, (bottom + 3*gap) - dp2px(3F) -5F, paintText)
        }
    }

    fun setDateForSelect(flag: Boolean){
        mDateForSelect = flag
    }


    fun setList(list: List<CalendarMemo>){
        mList = list
    }

    fun getDateForSelect(): Boolean = mDateForSelect

    fun getDate(): DateTime = date

    fun getYear() = date.year
    fun getMonth() = date.monthOfYear
    fun getDay() = date.dayOfMonth

    fun dp2px(dp:Float): Float{
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}