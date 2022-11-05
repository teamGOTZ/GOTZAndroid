package com.gotz.presentation.view.calendar.calendar

import android.os.Bundle
import com.gotz.base.BaseFragment
import com.gotz.base.util.CalendarUtil
import com.gotz.presentation.R
import com.gotz.presentation.databinding.FragmentCalendarShortBinding
import org.joda.time.DateTime

class CalendarShortFragment: BaseFragment<FragmentCalendarShortBinding>(R.layout.fragment_calendar_short) {

    private var millis: Long = 0L

    companion object{

        private const val MILLIS_SHORT = "MILLIS_SHORT"

        fun newInstance(millis: Long) = CalendarShortFragment().apply {
            arguments = Bundle().apply{
                putLong(MILLIS_SHORT, millis)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            millis = it.getLong(CalendarShortFragment.MILLIS_SHORT)
        }
    }

    override fun initFragment() {

    }

    override fun initView() {
        binding.calendarShortView.initCalendar(DateTime(millis), CalendarUtil.getWeekList(DateTime(millis)),0)
    }

    //    private val viewModel: CalendarViewModel by activityViewModels{
//        CalendarViewModelFactory((requireActivity().application as BaseApplication).roomCalendarMemoRepositoryImpl)
//    }
//

//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        try{
//            viewModel.calendarMemo.observe(requireActivity(), Observer {
//                binding.calendarShortView.initCalendar(DateTime(millis), CalendarUtil.getWeekList(DateTime(millis)),0, it)
//                setItemClicked()
//
//                viewModel.calendarShortView = binding.calendarShortView
//                viewModel.flagForShortPosition.value = true
//            })
//        }catch (e:Exception){
//            Log.e("CalendarShort", e.message.toString())
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        with(viewModel){
//            if(flagForSelectedItem.value!!){
//                val year = year.value!!
//                val month = month.value!!
//                val day = day.value!!
//
//                val dateTime1 = startDayOfWeek(DateTime(millis))
//                val dateTime2 = startDayOfWeek(DateTime(year, month, day, 0, 0, 0))
//                if(dateTime1.year == dateTime2.year && dateTime1.monthOfYear == dateTime2.monthOfYear && dateTime1.dayOfMonth == dateTime2.dayOfMonth){
//                    flagForSelectedItem.value = false
//
//                    calendarShortView = binding.calendarShortView
//                    flagForShortPosition.value = true
//                    flagForShortUpdate.value = true
//                }
//            }
//            else{
//                year.value = startDayOfWeek(DateTime(millis)).year
//                month.value = startDayOfWeek(DateTime(millis)).monthOfYear
//                day.value = startDayOfWeek(DateTime(millis)).dayOfMonth
//
//                calendarShortView = binding.calendarShortView
//                flagForShortPosition.value = true
//                flagForShortUpdate.value = true
//            }
//            flagForList.value = true
//
//            flagForShortPosition.observe(requireActivity(), Observer {
//                if(flagForShortPosition.value!!){
//                    if(flagForSelected.value!!) setPosition()
//                    else erasePosition()
//
//                    flagForShortPosition.value = false
//                }
//            })
//
//            flagForShortUpdate.observe(requireActivity(), Observer {
//                if(flagForShortUpdate.value!! && calendarMemo.value != null){
//                    drawCalendar(calendarMemo.value!!)
//                    flagForShortUpdate.value = false
//                }
//            })
//        }
//    }
//
//    fun drawCalendar(list: List<CalendarMemo>){
//        try{
//            viewModel.calendarShortView.children.forEach {it as DayItemShortView
//                val year = it.getYear()
//                val month = it.getMonth()
//                val day = it.getDay()
//                val start = DateTime(year, month, day, 0, 0,0).millis
//                val end = DateTime(start).plusDays(1).millis
//                var count = 0
//                list.forEach{
//                    if(it.start >= start && it.start < end) count++
//                }
//                it.setCount(count)
//                it.invalidate()
//            }
//        }catch (e: Exception){
//            Log.e("CalendarShort", e.message.toString())
//        }
//    }
//
//    private fun setPosition(){
//        val year = viewModel.year.value!!
//        val month = viewModel.month.value!!
//        val day = viewModel.day.value!!
//
//        viewModel.calendarShortView.children.forEach { it as DayItemShortView
//            it.setDayOfWeek(0)
//        }
//        var position: Int = DateTime(year!!, month!!, day!!, 0, 0, 0).dayOfWeek
//        if (position == 7) position = 0
//
//        try{
//            (viewModel.calendarShortView.getChildAt(position) as DayItemShortView).setDayOfWeek(DateTime(year!!, month!!, day!!, 0, 0, 0).dayOfWeek)
//        }catch (e: Exception){
//            Log.e("CalendarShort", e.message.toString())
//        }
//
//        viewModel.calendarShortView.children.forEach {it as DayItemShortView
//            it.invalidate()
//        }
//    }
//
//    private fun erasePosition() {
//        viewModel.calendarShortView.children.forEach {it as DayItemShortView
//            it.setDayOfWeek(0)
//            it.invalidate()
//        }
//    }
//
//    private fun setItemClicked(){
//        binding.calendarShortView.children.forEach {
//            it.setOnClickListener { it as DayItemShortView
//                val year = it.getYear()
//                val month = it.getMonth()
//                val day = it.getDay()
//
//                binding.calendarShortView.children.forEach {
//                    (it as DayItemShortView).setDayOfWeek(0)
//                }
//                it.setDayOfWeek(DateTime(year, month, day, 0,0,0).dayOfWeek)
//                binding.calendarShortView.children.forEach {
//                    (it as DayItemShortView).invalidate()
//                }
//
//                viewModel.year.value = year
//                viewModel.month.value = month
//                viewModel.day.value = day
//
//                if(!viewModel.flagForSelected.value!!) viewModel.flagForSelected.value = true
//            }
//        }
//    }
//


}