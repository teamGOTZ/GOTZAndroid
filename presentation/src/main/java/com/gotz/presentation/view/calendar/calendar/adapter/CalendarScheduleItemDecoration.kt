package com.gotz.presentation.view.calendar.calendar.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.view.children
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.gotz.base.util.DimensionUtil.dpToPx
import com.gotz.presentation.util.GLog

class CalendarScheduleItemDecoration(val context: Context): RecyclerView.ItemDecoration()  {

    private val radius = dpToPx(context, 4F) - (dpToPx(context,1F)/2F)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = dpToPx(context, 1F)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = dpToPx(context, 8F).toInt()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        val firstIndex = 0
        val lastIndex = parent.children.lastIndexOf(parent.children.last())
        val paddingLarge = dpToPx(context, 40F).toInt()
        val paddingSmall = dpToPx(context, 16F).toInt()
        val paddingStart = dpToPx(context, 8F).toInt()
        GLog.messageLog("Decoration: $lastIndex")

        parent.children.forEachIndexed { index, childView ->
            val x = dpToPx(context,4F)
            val y = childView.top + dpToPx(context,24F)

            if(lastIndex > 0) {
                when (index) {
                    firstIndex -> {
                        canvas.drawLine(x, y + radius + paddingLarge, x, childView.bottom.toFloat(), paint)
                        childView.setPadding(paddingStart, paddingLarge, 0,paddingSmall)
                        canvas.drawCircle(x, y + paddingLarge, radius, paint)
                    }
                    lastIndex -> {
                        canvas.drawLine(x, childView.top.toFloat(), x, y - radius + paddingSmall, paint)
                        childView.setPadding(paddingStart, paddingSmall, 0,paddingLarge)
                        canvas.drawCircle(x, y + paddingSmall, radius, paint)
                    }
                    else -> {
                        canvas.drawLine(x, y + radius + paddingSmall, x, childView.bottom.toFloat(), paint)
                        canvas.drawLine(x, childView.top.toFloat(), x, y - radius + paddingSmall, paint)
                        childView.setPadding(paddingStart, paddingSmall, 0,paddingSmall)
                        canvas.drawCircle(x, y + paddingSmall, radius, paint)
                    }
                }
            }
            else{
                canvas.drawCircle(x, y + paddingLarge, radius, paint)
                childView.setPadding(paddingStart, paddingLarge, 0,paddingLarge)
            }


        }
    }
}