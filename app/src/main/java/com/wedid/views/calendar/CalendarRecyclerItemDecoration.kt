package com.wedid.views.calendar

import android.content.Context
import android.graphics.*
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class CalendarRecyclerItemDecoration(context: Context): RecyclerView.ItemDecoration() {
    private val context = context
    private var leftMargin = dp2px(22F).toInt()
    private var topMargin = dp2px(11F).toInt()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = dp2px(1F)
    }

    private val paintLineBottom = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#BDBDBD")
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = dp2px(1F)
        pathEffect = DashPathEffect(floatArrayOf(dp2px(6F),dp2px(2F)), 0F)
    }

    private val paintLineTop = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#BDBDBD")
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = dp2px(1F)
        pathEffect = DashPathEffect(floatArrayOf(dp2px(6F),dp2px(2F)), 13F)
    }

    private val paintFill = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = dp2px(1F)
    }

    private val paintNone = Paint(Paint.ANTI_ALIAS_FLAG).apply{
        color = Color.parseColor("#BDBDBD")
        style = Paint.Style.STROKE
        strokeWidth = dp2px(1F)
    }


    private val radius = dp2px(5F)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
            val tmp = (parent.adapter as CalendarRecyclerAdapter).getItemViewType(parent.getChildLayoutPosition(view))
            if(tmp == 0 || tmp == -3) outRect.left = leftMargin + dp2px(20F).toInt()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val layoutManager = parent.layoutManager
            for (view in parent.children) {
                // offset value
                val leftDocrationWidth = layoutManager!!.getLeftDecorationWidth(view)
                val topDecorationHeight = layoutManager!!.getTopDecorationHeight(view)

                val childLayoutPosition = parent.getChildLayoutPosition(view)

                val startX: Float = dp2px(20F) + dp2px(11F)
                val stopX: Float = startX

                val topStartY: Float = (view.top - topDecorationHeight).toFloat()
                //val topStopY: Float = (view.top + view.height / 2 - radius).toFloat()
                val topStopY: Float = (view.top + leftMargin/2F - radius) + topMargin

                //val bottomStartY: Float = (view.top + view.height / 2 + radius).toFloat()
                val bottomStartY: Float = (view.top + leftMargin/2F + radius) + topMargin
                val bottomStopY: Float = view.bottom.toFloat()

                val itemViewType: Int = (parent.adapter as CalendarRecyclerAdapter).getItemViewType(childLayoutPosition)

                fun listPosition(pos: Int): Int = (parent.adapter as CalendarRecyclerAdapter).getListPosition(pos)

                val list = (parent.adapter as CalendarRecyclerAdapter).lists
                val size = (parent.adapter as CalendarRecyclerAdapter).lists.size
                try {
                    if (itemViewType == 0) {
                        if (list.get(childLayoutPosition).isAllDay) {
                            // 일정 하루종일
                            //c.drawCircle(leftMargin/2F, (view.top + view.height / 2).toFloat(), radius, paintFill)
                            /*c.drawCircle(leftMargin / 2F + dp2px(20F),
                                view.top + leftMargin / 2F + topMargin,
                                radius,
                                paintFill)*/
                            c.drawCircle(leftMargin / 2F + dp2px(20F),
                                view.top + leftMargin / 2F + topMargin,
                                radius,
                                paint)
                        }  else {

                            if (listPosition(childLayoutPosition) == 1) { //size -2 -> 일정이 없습니다 생각한 사이즈 size -1로 될수도

                                if (childLayoutPosition != size - 2 && listPosition(childLayoutPosition + 1) != 0)
                                    c.drawLine(startX, bottomStartY, stopX, bottomStopY, paintLineBottom)
                            } else if (childLayoutPosition == size - 2 ||
                                listPosition(childLayoutPosition + 1) == 0 &&
                                listPosition(childLayoutPosition) != 0) {
                                c.drawLine(startX, topStartY, stopX, topStopY, paintLineTop)
                            } else {
                                c.drawLine(startX, topStartY, stopX, topStopY, paintLineTop)
                                c.drawLine(startX, bottomStartY, stopX, bottomStopY, paintLineBottom)
                            }

                            // 일정 시간 등록
                            /***** 시간비교 ****/
                            /*
                            if (list.get(childLayoutPosition).start < DateTime.now().millis) {
                                //c.drawCircle(leftMargin/2F, (view.top + view.height / 2).toFloat(), radius, paintFill)
                                c.drawCircle(leftMargin / 2F + dp2px(20F),
                                    view.top + leftMargin / 2F + topMargin,
                                    radius,
                                    paintFill)
                            } else {
                                //c.drawCircle(leftMargin/2F, (view.top + view.height / 2).toFloat(), radius, paint)
                                c.drawCircle(leftMargin / 2F + dp2px(20F),
                                    view.top + leftMargin / 2F + topMargin,
                                    radius,
                                    paint)
                            }*/
                            c.drawCircle(leftMargin / 2F + dp2px(20F),
                                view.top + leftMargin / 2F + topMargin,
                                radius,
                                paint)
                        }

                    }else if(itemViewType == -3){
                        c.drawCircle(leftMargin / 2F + dp2px(20F),
                            view.top + leftMargin / 2F + topMargin,
                            radius,
                            paintNone)
                    }
                }catch (e: Exception){
                    Log.e("ItemDecoration", e.message.toString())
                }
            }
    }


    fun dp2px(dp:Float): Float{
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}