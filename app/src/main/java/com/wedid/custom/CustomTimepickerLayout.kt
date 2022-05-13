package com.wedid.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.widget.LinearLayout

class CustomTimepickerLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val strokeW = 1F
    private val offset = dp2px(strokeW/2F)
    private val roundCorner = dp2px(4F)

    private val paint300: Paint = Paint()
    private val paint900: Paint = Paint()

    private var path300Start = Path()
    private var path900Start = Path()
    private var path300End = Path()
    private var path900End = Path()

    private var flag: Boolean = true

    init{
        setWillNotDraw(false)
        paint300.apply {
            isAntiAlias = true
            color = Color.parseColor("#E0E0E0")
            strokeWidth = dp2px(strokeW)
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.STROKE
            pathEffect = CornerPathEffect(roundCorner)
        }
        paint900.apply {
            isAntiAlias = true
            color = Color.BLACK
            strokeWidth = dp2px(strokeW)
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.STROKE
            pathEffect = CornerPathEffect(roundCorner)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        path300Start = Path().apply {
            lineTo(w.toFloat() - offset, offset)
            lineTo(w.toFloat() - offset, h.toFloat() - offset)
            lineTo(offset, h.toFloat() - offset)
        }

        path900Start = Path().apply {
            val rectPointX = w * 0.4436F
            val trianglePointX = w * 0.5352F
            val trianglePointY = h / 2F
            moveTo(offset, offset)
            lineTo(rectPointX, offset)
            lineTo(trianglePointX, trianglePointY)
            lineTo(rectPointX, h.toFloat() - offset)
            lineTo(offset, h.toFloat() - offset)
        }

        path300End = Path().apply {
            lineTo(w.toFloat() - offset, offset)
            lineTo(w.toFloat() - offset, h.toFloat() - offset)
            lineTo(offset, h.toFloat() - offset)
        }

        path900End = Path().apply {
            val rectPointX = w * 0.43F
            val trianglePointX = w * 0.52F
            val trianglePointY = h / 2F
            moveTo(rectPointX, offset)
            lineTo(trianglePointX, trianglePointY)
            lineTo(rectPointX, h.toFloat() - offset)
            lineTo(w.toFloat() - offset, h.toFloat() - offset)
            lineTo(w.toFloat() - offset, offset)
        }

        path300Start.close()
        path900Start.close()

        path300End.close()
        path900End.close()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(canvas == null) return

        if(flag){
            canvas.drawPath(path300Start, paint300)
            canvas.drawPath(path900Start, paint900)
        }
        else{
            canvas.drawPath(path300End, paint300)
            canvas.drawPath(path900End, paint900)
        }
    }

    fun setStart(){
        flag = true
        invalidate()
    }

    fun setEnd(){
        flag = false
        invalidate()
    }

    fun dp2px(dp:Float): Float{
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}