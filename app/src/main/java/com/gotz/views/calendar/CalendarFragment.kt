package com.gotz.views.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gotz.base.BaseApplication
import com.gotz.databinding.FragmentCalendarBinding
import com.gotz.util.CalendarUtil.Companion.startDayOfWeek
import com.gotz.views.calendar.memo.CalendarMemoActivity
import com.gotz.views.frame.FrameActivity
import org.joda.time.DateTime
import kotlin.Exception

class CalendarFragment: Fragment(){
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CalendarViewModel by activityViewModels{
        CalendarViewModelFactory((requireActivity().application as BaseApplication).roomCalendarMemoRepositoryImpl)
    }

    private lateinit var calendarRecyclerAdapter: CalendarRecyclerAdapter

    private lateinit var calendarLongAdapter: CalendarLongAdapter
    private lateinit var calendarShortAdapter: CalendarShortAdapter

    private val list: ArrayList<CalendarRecyclerItem> = ArrayList()
    private val doublelist: ArrayList<ArrayList<CalendarRecyclerItem>> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        calendarLongAdapter = CalendarLongAdapter(requireActivity())
        calendarShortAdapter = CalendarShortAdapter(requireActivity())
        calendarRecyclerAdapter = CalendarRecyclerAdapter(requireActivity())

        binding.rvList.adapter = calendarRecyclerAdapter

        binding.vpCalendarLong.adapter = calendarLongAdapter
        binding.vpCalendarShort.adapter = calendarShortAdapter

        binding.vpCalendarLong.setCurrentItem(CalendarLongAdapter.START_POSITION, false)
        binding.vpCalendarShort.setCurrentItem(CalendarShortAdapter.START_POSITION, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFragmentCalendarDate.text = viewModel.year.toString() + "년 " + viewModel.month.toString() + "월"
        initObserver()
        btnClick()
        setDecoration()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        try{
            val dateTime = DateTime(viewModel.year.value!!, viewModel.month.value!!, viewModel.day.value!!, 0, 0, 0)
            setList(dateTime)

            viewModel.calendarMemo.observe(requireActivity(), Observer {
                viewModel.flagForShortUpdate.value = true
                viewModel.flagForLongUpdate.value = true
            })
        }catch (e: Exception){
            Log.e("CalendarFragment", e.message.toString())
        }

        viewModel.flagForList.observe(requireActivity(), Observer {
            if(viewModel.flagForList.value!!){
                setList(DateTime(viewModel.year.value!!, viewModel.month.value!!, viewModel.day.value!!,0,0,0))
                viewModel.flagForList.value = false
            }
        })

    }

    fun refreshFragment(){
        with(viewModel){
            val dateTime = DateTime.now()
            if(year.value != dateTime.year || month.value != dateTime.monthOfYear || day.value != dateTime.dayOfMonth){
                flagForSelectedItem.value = true
                flagForSelected.value = false

                year.value = dateTime.year
                month.value = dateTime.monthOfYear
                day.value = dateTime.dayOfMonth
                closeCalendar()
            }
        }
    }

    private fun initViewModel(){
        with(viewModel){
            year.value = DateTime.now().year
            month.value = DateTime.now().monthOfYear
            day.value = DateTime.now().dayOfMonth

            flagForSelectedItem.value = true
            flagForSelected.value = false
        }
    }

    private fun initObserver(){
        viewModel.year.observe( viewLifecycleOwner, Observer {
            binding.tvFragmentCalendarDate.text = viewModel.year.value.toString() + "년 " + viewModel.month.value.toString() + "월"
        })

        viewModel.month.observe( viewLifecycleOwner, Observer {
            binding.tvFragmentCalendarDate.text = viewModel.year.value.toString() + "년 " + viewModel.month.value.toString() + "월"
        })
    }

    private fun btnClick(){

        binding.btnFragmentCalendarBefore.setOnClickListener{
            viewModel.flagForSelectedItem.value = true
            var dateTime = DateTime(viewModel.year.value!!, viewModel.month.value!!, 1, 0, 0, 0)
            dateTime = dateTime.minusMonths(1)
            setCalendar(dateTime)
        }

        binding.btnFragmentCalendarAfter.setOnClickListener {
            viewModel.flagForSelectedItem.value = true
            var dateTime = DateTime(viewModel.year.value!!, viewModel.month.value!!, 1, 0, 0, 0)
            dateTime = dateTime.plusMonths(1)
            setCalendar(dateTime)
        }

        binding.btnOpen.setOnClickListener {
            openCalendar()
        }

        binding.btnClose.setOnClickListener {
            closeCalendar()
        }

        binding.fabFragmentCalendar.setOnClickListener {
            if(viewModel.flagForSelected.value!!) (requireActivity() as FrameActivity).openActivityForResult(CalendarMemoActivity(),-1, DateTime(viewModel.year.value!!, viewModel.month.value!!, viewModel.day.value!!, 0, 0, 0).millis)
            else (requireActivity() as FrameActivity).openActivityForResult(CalendarMemoActivity(),-1)
        }
    }

    private fun setCalendar(dateTime:DateTime){
        viewModel.year.value = dateTime.year
        viewModel.month.value = dateTime.monthOfYear
        viewModel.day.value = dateTime.dayOfMonth

        val startState = binding.mlCalendar.startState
        val endState = binding.mlCalendar.endState
        if(binding.mlCalendar.currentState.equals(startState)){
            binding.vpCalendarShort.currentItem = CalendarShortAdapter.START_POSITION + getShortPosition(dateTime)
            viewModel.flagForShortPosition.value = true
        }

        if(binding.mlCalendar.currentState.equals(endState)){
            binding.vpCalendarLong.currentItem = CalendarLongAdapter.START_POSITION + getLongPosition(dateTime)
            viewModel.flagForLongPosition.value = true
        }
    }

    private fun setList(_dateTime:DateTime){
            val dateTime = startDayOfWeek(_dateTime)

            viewModel.calendarMemo.observe(requireActivity(), Observer {
                doublelist.clear()
                for (idx in 0 until 7) {
                    // 여기서 SELECT 조건 하루치만 설정해서 데이터 조정
                    val idxDateTime = dateTime.plusDays(idx)
                    val tmp = arrayListOf<CalendarRecyclerItem>()
                    for (item in it) {
                        if (item.start >= idxDateTime.millis && item.start < idxDateTime.plusDays(1).millis)
                            tmp.add(CalendarRecyclerItem(-1,
                                item.title,
                                item.content,
                                item.isAllDay,
                                item.start,
                                item.end,
                                item.uid))
                    }
                    if(tmp.size == 0)tmp.add(CalendarRecyclerItem(title = "일정이 없습니다.", idx = -3))
                    doublelist.add(tmp)
                }
                list.clear()
                list.add(CalendarRecyclerItem(start = 1, end = 0, idx = -2))
                for (idx in doublelist.indices) {
                    list.add(CalendarRecyclerItem(dateTime.plusDays(idx).dayOfMonth, start = dateTime.plusDays(idx).millis))

                    for (item in doublelist.get(idx)) {
                        list.add(item)
                    }

                    if(idx != 6)list.add(CalendarRecyclerItem(start = 0, end = 0, idx = -2))
                }
                list.add(CalendarRecyclerItem(start = 0, end = 1, idx = -2))

                calendarRecyclerAdapter.setData(list)
                binding.rvList.scrollToPosition(0)
            })
    }

    private fun setDecoration(){
        val decoration = CalendarRecyclerItemDecoration(requireContext())
        binding.rvList.addItemDecoration(decoration)
    }

    fun openCalendar(){
        binding.mlCalendar.transitionToEnd()
        binding.fabFragmentCalendar.visibility = View.GONE

        val dateTime = DateTime(viewModel.year.value!!, viewModel.month.value!!, viewModel.day.value!!, 0, 0, 0)
        setCalendar(dateTime)
    }

    fun closeCalendar(){
        binding.mlCalendar.transitionToStart()
        binding.fabFragmentCalendar.visibility = View.VISIBLE
        viewModel.flagForSelectedItem.value = true

        val dateTime = DateTime(viewModel.year.value!!, viewModel.month.value!!, viewModel.day.value!!, 0, 0, 0)
        setCalendar(dateTime)
    }

    companion object{
        fun getLongPosition(dateTime: DateTime): Int{
            val year1:Int = dateTime.year
            val year2:Int = DateTime.now().year
            val month1:Int = dateTime.monthOfYear
            val month2:Int = DateTime.now().monthOfYear

            return (year1 - year2)*12 + (month1 - month2)
        }

        fun getShortPosition(dateTime: DateTime): Int{
            val dateTime1: DateTime = startDayOfWeek(dateTime)
            val dateTime2: DateTime = startDayOfWeek(DateTime(DateTime.now().year, DateTime.now().monthOfYear, DateTime.now().dayOfMonth, 0,0,0))

            return ((dateTime1.millis - dateTime2.millis)/604800000L).toInt()
        }
    }
}