package com.gotz.presentation.view.calendar.calendar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gotz.base.BaseRecyclerAdapter
import com.gotz.base.BaseRecyclerViewHolder
import com.gotz.base.extension.invisible
import com.gotz.base.extension.visible
import com.gotz.base.util.StringUtil.getStrTime2
import com.gotz.base.util.StringUtil.getAmPm
import com.gotz.domain.model.Schedule
import com.gotz.presentation.R
import com.gotz.presentation.databinding.ItemCalendarScheduleBinding
import org.joda.time.DateTime

class CalendarScheduleAdapter: BaseRecyclerAdapter<Schedule, ItemCalendarScheduleBinding>(R.layout.item_calendar_schedule) {

    private var onLayoutClickListener: OnLayoutClickListener? = null

    override fun createHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<Schedule, ItemCalendarScheduleBinding> =
        CalendarScheduleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_schedule, parent, false))


    fun setOnLayoutClickListener(onLayoutClickListener: OnLayoutClickListener){
        this.onLayoutClickListener = onLayoutClickListener
    }

    inner class CalendarScheduleViewHolder(itemView: View): BaseRecyclerViewHolder<Schedule, ItemCalendarScheduleBinding>(itemView){

        override fun bindData(item: Schedule) {
            binding?.run {
                if(item.isAllDay){
                    tvAmPm.invisible()

                    tvStartTime.text = "종일"
                }
                else {
                    tvAmPm.visible()

                    tvStartTime.text = getStrTime2(DateTime(item.start))
                    tvAmPm.text = getAmPm(DateTime(item.start))
                }

                tvTitle.text = item.title
                tvContents.text = item.content

                clContent.setOnClickListener {
                    onLayoutClickListener?.onLayoutClick(it, item)
                }
            }
        }
    }

    interface OnLayoutClickListener {
        fun onLayoutClick(view: View, item: Schedule)
    }
}

