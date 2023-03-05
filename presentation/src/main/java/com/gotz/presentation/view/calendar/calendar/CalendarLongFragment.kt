package com.gotz.presentation.view.calendar.calendar

import android.os.Bundle
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.gotz.presentation.base.BaseFragment
import com.gotz.presentation.custom.calendar.DayItemLongView
import com.gotz.presentation.util.CalendarUtil
import com.gotz.presentation.util.CalendarUtil.getDateEndOfMonth
import com.gotz.presentation.util.CalendarUtil.getDateStartOfMonth
import com.gotz.presentation.util.CalendarUtil.isSameDay
import com.gotz.presentation.util.CalendarUtil.isSameMonth
import com.gotz.domain.model.ScheduleWithDate
import com.gotz.presentation.R
import com.gotz.presentation.databinding.FragmentCalendarLongBinding
import com.gotz.presentation.view.calendar.schedule.ScheduleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarLongFragment :
    BaseFragment<FragmentCalendarLongBinding>(R.layout.fragment_calendar_long) {

    private val calendarViewModel: CalendarViewModel by sharedViewModel()
    private val scheduleViewModel: ScheduleViewModel by viewModel()

    private var millis: Long = 0L

    companion object {

        private const val MILLIS = "MILLIS"

        fun newInstance(millis: Long) = CalendarLongFragment().apply {
            arguments = Bundle().apply {
                putLong(MILLIS, millis)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            millis = it.getLong(MILLIS)
        }
    }

    override fun initFragment() {

    }

    override fun initView() {
        binding.calendarLongView.initCalendar(
            DateTime(millis),
            CalendarUtil.getMonthList(DateTime(millis))
        )

        setItemClicked()
    }

    override fun onResume() {
        super.onResume()
        scheduleViewModel.run {
            lifecycleScope.launch(Dispatchers.IO) {
                val scheduleCountList = scheduleViewModel.readMonthlyScheduleUseCase(
                    getDateStartOfMonth(DateTime(millis)),
                    getDateEndOfMonth(DateTime(millis))).first()

                withContext(Dispatchers.Main) {
                    binding.calendarLongView.updateCalendar(getScheduleCountList(DateTime(millis), scheduleCountList))
                    setItemClicked()
                }
            }
        }
    }

    private fun setItemClicked() {
        calendarViewModel.dateTime.observe(this) { dateTime2 ->
            binding.run{
                calendarLongView.children.forEach { childView -> (childView as DayItemLongView)
                    childView.setOnClickListener { dayItemLongView -> (dayItemLongView as DayItemLongView)
                        val dateTime1 = dayItemLongView.getDateTime()

                        if (isSameMonth(dateTime1, dateTime2)) {
                            invalidateView(dayItemLongView)
                            calendarViewModel.setDateTime(dateTime1.millis)
                            calendarViewModel.setClickStatus(true)
                        }
                    }

                    updateChildView(childView, dateTime2)
                }
            }
        }
    }

    private fun updateChildView(dayItemLongView: DayItemLongView, dateTime2: DateTime){
        if(isSameDay(dayItemLongView.getDateTime(), dateTime2) &&
                calendarViewModel.clickStatus.value == true){
            invalidateView(dayItemLongView)
        }
    }

    private fun invalidateView(dayItemLongView: DayItemLongView) {
        binding.run {
            calendarLongView.children.forEach { child -> (child as DayItemLongView)
                child.setDateForSelect(false)
            }

            dayItemLongView.setDateForSelect(true)
            calendarLongView.children.forEach { child -> (child as DayItemLongView)
                child.invalidate()
            }
        }
    }

    private fun getScheduleCountList(dateTime: DateTime, list: List<ScheduleWithDate>): List<Int> {

        val date = dateTime.withDayOfMonth(1)
        val prev = CalendarUtil.getPrevOffSet(date)
        val startValue = date.minusDays(prev)
        val totalDay = DateTimeConstants.DAYS_PER_WEEK * CalendarUtil.WEEKS_PER_MONTH

        val result = mutableListOf<Int>()
        for (i in 0 until totalDay) {
            val day = startValue.plusDays(i)
            result.add(getScheduleCount(day, list))
        }

        return result
    }


    private fun getScheduleCount(dateTime: DateTime, list: List<ScheduleWithDate>): Int {
        val date = dateTime.toString("yyyy-MM-dd")

        val scheduleForDate = list.filter { scheduleWithDate ->
            scheduleWithDate.date.dateId == date
        }

        return if(scheduleForDate.isEmpty()) 0
        else scheduleForDate[0].schedule.count()
    }
}