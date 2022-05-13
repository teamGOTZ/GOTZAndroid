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
import com.wedid.custom.calendar.WeekDayItemView
import com.wedid.database.calendarmemo.CalendarMemo
import com.wedid.databinding.FragmentCalendarShortBinding
import com.wedid.util.CalendarUtil
import com.wedid.util.CalendarUtil.Companion.startDayOfWeek
import org.joda.time.DateTime

class CalendarShortFragment:Fragment() {
    private val viewModel: CalendarViewModel by activityViewModels{
        CalendarViewModelFactory((requireActivity().application as BaseApplication).roomCalendarMemoRepositoryImpl)
    }
    private var _binding: FragmentCalendarShortBinding? = null
    private val binding get() = _binding!!

    private var millis: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            millis = it.getLong(CalendarShortFragment.MILLIS_SHORT)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCalendarShortBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try{
            viewModel.calendarMemo.observe(requireActivity(), Observer {
                binding.weekCalendarView.initCalendar(DateTime(millis), CalendarUtil.getWeekList(DateTime(millis)),0, it)
                setPosition()
                setItmeClicked()
            })
        }catch (e:Exception){
            Log.e("CalendarShort", e.message.toString())
        }
    }

    fun drawCalendar(list: List<CalendarMemo>){
        binding.weekCalendarView.children.forEach { it as WeekDayItemView
            //it.invalidate()
            val year = it.getYear()
            val month = it.getMonth()
            val day = it.getDay()
            val start = DateTime(year, month, day, 0, 0,0).millis
            val end = DateTime(start).plusDays(1).millis
            var count = 0
            list.forEach{
                if(it.start >= start && it.start < end) count++
            }
            it.setCount(count)
            it.invalidate()
        }

    }

    override fun onResume() {
        super.onResume()

        if(viewModel.flagForShort.value!!){
            viewModel.year.value = startDayOfWeek(DateTime(millis)).year
            viewModel.month.value = startDayOfWeek(DateTime(millis)).monthOfYear
            viewModel.day.value = startDayOfWeek(DateTime(millis)).dayOfMonth

            //viewModel.flagForShort.value = false
        }
        else{

        }

        val dateTime1 = startDayOfWeek(DateTime(viewModel.year.value!!, viewModel.month.value!!, viewModel.day.value!!, 0, 0, 0))
        val dateTime2 = startDayOfWeek(DateTime(DateTime(millis).year, DateTime(millis).monthOfYear, DateTime(millis).dayOfMonth, 0, 0, 0))

        if( dateTime1 == dateTime2){
            viewModel.flagForShort.value = true
        }

        (requireActivity().supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0) as CalendarFragment).setShortFragment(this)
        setPosition()
    }

    fun setPosition(){
        val year = viewModel.year.value!!
        val month = viewModel.month.value!!
        val day = viewModel.day.value!!

        val dateTime = DateTime(year, month, day, 0, 0, 0)

        if(viewModel.flagForSelect.value!!){


            binding.weekCalendarView.children.forEach {
                (it as WeekDayItemView).setDayOfWeek(0)
            }
            var position: Int = DateTime(year!!, month!!, day!!, 0, 0, 0).dayOfWeek
            if (position == 7) position = 0

            try{
                (binding.weekCalendarView.getChildAt(position) as WeekDayItemView).setDayOfWeek(DateTime(year!!, month!!, day!!, 0, 0, 0).dayOfWeek)
            }catch (e: Exception){
                Log.e("SHORT", e.message.toString())
            }


            binding.weekCalendarView.children.forEach {
                (it as WeekDayItemView).invalidate()
            }

        }
        (requireActivity().supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0) as CalendarFragment).setList(dateTime)

    }

    fun setItmeClicked(){

        binding.weekCalendarView.children.forEach {
            it.setOnClickListener { (it as WeekDayItemView)
                val year = it.getYear()
                val month = it.getMonth()
                val day = it.getDay()

                binding.weekCalendarView.children.forEach {
                    (it as WeekDayItemView).setDayOfWeek(0)
                }
                it.setDayOfWeek(DateTime(year, month, day, 0,0,0).dayOfWeek)
                binding.weekCalendarView.children.forEach {
                    (it as WeekDayItemView).invalidate()
                }

                viewModel.year.value = year
                viewModel.month.value = month
                viewModel.day.value = day

                viewModel.flagForSelect.value = true
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{

        private const val MILLIS_SHORT = "MILLIS_SHORT"

        fun newInstance(millis: Long) = CalendarShortFragment().apply {
            arguments = Bundle().apply{
                putLong(MILLIS_SHORT, millis)
            }
        }
    }
}