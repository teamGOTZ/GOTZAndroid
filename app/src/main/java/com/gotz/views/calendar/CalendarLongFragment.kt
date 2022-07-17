package com.gotz.views.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gotz.base.BaseApplication
import com.gotz.custom.calendar.DayItemLongView
import com.gotz.database.calendarmemo.CalendarMemo
import com.gotz.databinding.FragmentCalendarLongBinding
import com.gotz.util.CalendarUtil.Companion.DAY_OF_START
import com.gotz.util.CalendarUtil.Companion.getMonthList
import com.gotz.util.CalendarUtil.Companion.isSameMonth
import org.joda.time.DateTime

class CalendarLongFragment: Fragment() {
    private val viewModel: CalendarViewModel by activityViewModels{
        CalendarViewModelFactory((requireActivity().application as BaseApplication).roomCalendarMemoRepositoryImpl)
    }
    private var _binding: FragmentCalendarLongBinding? = null
    private val binding get() = _binding!!

    private var millis: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            millis = it.getLong(MILLIS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCalendarLongBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try{
            viewModel.calendarMemo.observe(requireActivity(), Observer {
                binding.calendarLongView.initCalendar(DateTime(millis),getMonthList(DateTime(millis)), it)
                setItemClicked()

                viewModel.calendarLongView = binding.calendarLongView
                viewModel.flagForLongPosition.value = true
            })
        }catch (e: Exception){
            Log.e("CalendarLong", e.message.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        with(viewModel){
            if(DateTime(millis).year != year.value || DateTime(millis).monthOfYear != month.value){
                year.value = DateTime(millis).year
                month.value = DateTime(millis).monthOfYear
                day.value = DAY_OF_START

                calendarLongView = binding.calendarLongView
                flagForLongPosition.value = true
                flagForLongUpdate.value = true
            }

            flagForLongPosition.observe(requireActivity(), Observer {
                if(flagForLongPosition.value!!){
                    if(flagForSelected.value!!) setPosition()
                    else erasePosition()

                    flagForLongPosition.value = false
                }
            })

            flagForLongUpdate.observe(requireActivity(), Observer {
                if(flagForLongUpdate.value!! && calendarMemo.value != null){
                    drawCalendar(calendarMemo.value!!)
                    flagForLongUpdate.value = false
                }
            })
        }
    }

    fun drawCalendar(list: List<CalendarMemo>){
        try{
            viewModel.calendarLongView.children.forEach { it as DayItemLongView
                //it.invalidate()
                val year = it.getYear()
                val month = it.getMonth()
                val day = it.getDay()
                val start = DateTime(year, month, day, 0, 0,0).millis
                val end = DateTime(start).plusDays(1).millis
                val lists = ArrayList<CalendarMemo>()
                list.forEach{
                    if(it.start >= start && it.start < end){
                        lists.add(it)
                    }
                }
                it.setList(lists)
                it.invalidate()
            }
        }catch (e: Exception){
            Log.e("CalendarLong", e.message.toString())
        }
    }

    private fun setPosition(){
        with(viewModel){
            val year = viewModel.year.value!!
            val month = viewModel.month.value!!
            val day = viewModel.day.value!!
            calendarLongView.forEach {it as DayItemLongView
                if(it.getDate().year == year && it.getDate().monthOfYear == month && it.getDate().dayOfMonth == day) it.setDateForSelect(true)
                else it.setDateForSelect(false)
                it.invalidate()
            }
        }
    }

    private fun erasePosition() {
        viewModel.calendarLongView.children.forEach {it as DayItemLongView
            it.setDateForSelect(false)
            it.invalidate()
        }
    }

    private fun setItemClicked(){
        binding.calendarLongView.children.forEach {
            it.setOnClickListener { (it as DayItemLongView)
                val year = it.getYear()
                val month = it.getMonth()
                val day = it.getDay()

                var dateTime = DateTime(year, month, day, 0,0,0)

                if(isSameMonth(dateTime, DateTime(viewModel.year.value!!, viewModel.month.value!!, 1, 0, 0, 0))){
                    if(it.getDateForSelect()){
                        (requireActivity().supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0) as CalendarFragment).closeCalendar()
                    }
                    else{
                        /*******  invalidate *******/
                        binding.calendarLongView.children.forEach {it as DayItemLongView
                            it.setDateForSelect(false)
                        }

                        it.setDateForSelect(true)
                        binding.calendarLongView.children.forEach {it as DayItemLongView
                            it.invalidate()
                        }

                        viewModel.year.value = year
                        viewModel.month.value = month
                        viewModel.day.value = day

                    }
                    if(!viewModel.flagForSelected.value!!)viewModel.flagForSelected.value = true
                }
            }
        }
    }

    companion object{

        private const val MILLIS = "MILLIS"

        fun newInstance(millis: Long) = CalendarLongFragment().apply {
            arguments = Bundle().apply{
                putLong(MILLIS, millis)
            }
        }
    }

}