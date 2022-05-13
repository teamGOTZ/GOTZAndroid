package com.wedid.views.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.wedid.base.BaseApplication
import com.wedid.custom.calendar.DayItemView
import com.wedid.database.calendarmemo.CalendarMemo
import com.wedid.databinding.FragmentCalendarLongBinding
import com.wedid.util.CalendarUtil
import com.wedid.util.CalendarUtil.Companion.DAY_OF_START
import com.wedid.util.CalendarUtil.Companion.getMonthList
import com.wedid.util.CalendarUtil.Companion.isSameMonth
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

        // 여기서 일정정보
        viewModel.calendarMemo.observe(requireActivity(), Observer {
            binding.calendarView.initCalendar(DateTime(millis),getMonthList(DateTime(millis)), it)
            setPosition()
            setItmeClicked()
        })


    }

    override fun onResume() {
        super.onResume()

        if(DateTime(millis).year != viewModel.year.value || DateTime(millis).monthOfYear != viewModel.month.value){
            viewModel.year.value = DateTime(millis).year
            viewModel.month.value = DateTime(millis).monthOfYear
            viewModel.day.value = DAY_OF_START
        }

        val dateTime = DateTime(viewModel.year.value!!, viewModel.month.value!!, viewModel.day.value!!,0,0,0)
        (requireActivity().supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0) as CalendarFragment).setList(dateTime)

        (requireActivity().supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0) as CalendarFragment).setLongFragment(this)
        setPosition()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun drawCalendar(list: List<CalendarMemo>){
        binding.calendarView.children.forEach { it as DayItemView
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

    }

    fun setItmeClicked(){
        binding.calendarView.children.forEach {
            it.setOnClickListener { (it as DayItemView)
                val year = it.getYear()
                val month = it.getMonth()
                val day = it.getDay()

                var dateTime: DateTime = DateTime(year, month, day, 0,0,0)

                if(isSameMonth(dateTime, DateTime(viewModel.year.value!!, viewModel.month.value!!, 1, 0, 0, 0))){
                    if(it.getDateForSelect()){

                        (requireActivity().supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0) as CalendarFragment).closeCalendar()
                        // 같은 주 일때 resume 안일어남
                    }
                    else{
                        /*******  invalidate *******/
                        binding.calendarView.children.forEach {
                            (it as DayItemView).setDateForSelect(false)
                        }

                        it.setDateForSelect(true)
                        binding.calendarView.children.forEach {
                            (it as DayItemView).invalidate()
                        }
                        val dateTime1 = CalendarUtil.startDayOfWeek(DateTime(viewModel.year.value!!,
                            viewModel.month.value!!,
                            viewModel.day.value!!,
                            0,
                            0,
                            0))
                        val dateTime2 = CalendarUtil.startDayOfWeek(DateTime(year,
                            month,
                            day,
                            0,
                            0,
                            0))

                        if(dateTime1 == dateTime2) viewModel.flagForShort.value = true
                        else viewModel.flagForShort.value = false

                        viewModel.year.value = year
                        viewModel.month.value = month
                        viewModel.day.value = day

                    }
                }
                viewModel.flagForSelect.value = true
            }
        }

    }

    fun setPosition(){
        val year = viewModel.year.value!!
        val month = viewModel.month.value!!
        val day = viewModel.day.value!!
        val dateTime = DateTime(year, month, day, 0, 0, 0)

        if(viewModel.flagForSelect.value!!){

            try{
                binding.calendarView.children.forEach {it as DayItemView
                    if(it.getDate().year == year && it.getDate().monthOfYear == month && it.getDate().dayOfMonth == day) it.setDateForSelect(true)
                    else it.setDateForSelect(false)
                    it.invalidate()
                }
            }catch (e: Exception){
                Log.e("CalendarLong", e.message.toString())
            }

        }
        (requireActivity().supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0) as CalendarFragment).setList(dateTime)

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