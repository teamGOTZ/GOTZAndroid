package com.gotz.presentation.view.calendar.calendar.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gotz.presentation.view.calendar.calendar.CalendarFragment.Companion.START_POSITION
import com.gotz.presentation.view.calendar.calendar.CalendarShortFragment
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants.SUNDAY

class CalendarShortAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

//    private var start: Long = DateTime().withDayOfWeek(SUNDAY).minusWeeks(1).withTimeAtStartOfDay().millis
    private var start: Long = DateTime(DateTime().year, DateTime().monthOfYear, DateTime().dayOfMonth, 0,0,0).withTimeAtStartOfDay().millis
    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        val millis = getItemId(position)
        return CalendarShortFragment.newInstance(millis)
    }

    override fun getItemId(position: Int): Long = DateTime(start).plusWeeks(position - START_POSITION).millis

    override fun containsItem(itemId: Long): Boolean {
        val date = DateTime(itemId)

        return date.dayOfMonth == 1 && date.millisOfDay == 0
    }
}