package com.gotz.presentation.view.calendar.calendar

import android.os.Bundle
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.gotz.base.BaseFragment
import com.gotz.base.custom.calendar.DayItemLongView
import com.gotz.base.util.CalendarUtil
import com.gotz.base.util.CalendarUtil.getDateEndOfMonth
import com.gotz.base.util.CalendarUtil.getDateStartOfMonth
import com.gotz.base.util.CalendarUtil.isSameDay
import com.gotz.base.util.CalendarUtil.isSameMonth
import com.gotz.domain.model.ScheduleWithDate
import com.gotz.presentation.R
import com.gotz.presentation.databinding.FragmentCalendarLongBinding
import com.gotz.presentation.util.GLog
import com.gotz.presentation.view.calendar.calendar.CalendarViewModel.Companion.CALENDAR_MONTH
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
    //    private val viewModel: CalendarViewModel by activityViewModels{
//        CalendarViewModelFactory((requireActivity().application as BaseApplication).roomCalendarMemoRepositoryImpl)
//    }
//    private var _binding: FragmentCalendarLongBinding? = null
//    private val binding get() = _binding!!
//

//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let{
//            millis = it.getLong(MILLIS)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        _binding = FragmentCalendarLongBinding.inflate(inflater,container,false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        try{
//            viewModel.calendarMemo.observe(requireActivity(), Observer {
//                binding.calendarLongView.initCalendar(DateTime(millis),getMonthList(DateTime(millis)), it)
//                setItemClicked()
//
//                viewModel.calendarLongView = binding.calendarLongView
//                viewModel.flagForLongPosition.value = true
//            })
//        }catch (e: Exception){
//            Log.e("CalendarLong", e.message.toString())
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        with(viewModel){
//            if(DateTime(millis).year != year.value || DateTime(millis).monthOfYear != month.value){
//                year.value = DateTime(millis).year
//                month.value = DateTime(millis).monthOfYear
//                day.value = DAY_OF_START
//
//                calendarLongView = binding.calendarLongView
//                flagForLongPosition.value = true
//                flagForLongUpdate.value = true
//            }
//
//            flagForLongPosition.observe(requireActivity(), Observer {
//                if(flagForLongPosition.value!!){
//                    if(flagForSelected.value!!) setPosition()
//                    else erasePosition()
//
//                    flagForLongPosition.value = false
//                }
//            })
//
//            flagForLongUpdate.observe(requireActivity(), Observer {
//                if(flagForLongUpdate.value!! && calendarMemo.value != null){
//                    drawCalendar(calendarMemo.value!!)
//                    flagForLongUpdate.value = false
//                }
//            })
//        }
//    }
//
//    fun drawCalendar(list: List<CalendarMemo>){
//        try{
//            viewModel.calendarLongView.children.forEach { it as DayItemLongView
//                //it.invalidate()
//                val year = it.getYear()
//                val month = it.getMonth()
//                val day = it.getDay()
//                val start = DateTime(year, month, day, 0, 0,0).millis
//                val end = DateTime(start).plusDays(1).millis
//                val lists = ArrayList<CalendarMemo>()
//                list.forEach{
//                    if(it.start >= start && it.start < end){
//                        lists.add(it)
//                    }
//                }
//                it.setList(lists)
//                it.invalidate()
//            }
//        }catch (e: Exception){
//            Log.e("CalendarLong", e.message.toString())
//        }
//    }
//
//    private fun setPosition(){
//        with(viewModel){
//            val year = viewModel.year.value!!
//            val month = viewModel.month.value!!
//            val day = viewModel.day.value!!
//            calendarLongView.forEach {it as DayItemLongView
//                if(it.getDate().year == year && it.getDate().monthOfYear == month && it.getDate().dayOfMonth == day) it.setDateForSelect(true)
//                else it.setDateForSelect(false)
//                it.invalidate()
//            }
//        }
//    }
//
//    private fun erasePosition() {
//        viewModel.calendarLongView.children.forEach {it as DayItemLongView
//            it.setDateForSelect(false)
//            it.invalidate()
//        }
//    }
//
//    private fun setItemClicked(){
//        binding.calendarLongView.children.forEach {
//            it.setOnClickListener { (it as DayItemLongView)
//                val year = it.getYear()
//                val month = it.getMonth()
//                val day = it.getDay()
//
//                var dateTime = DateTime(year, month, day, 0,0,0)
//
//                if(isSameMonth(dateTime, DateTime(viewModel.year.value!!, viewModel.month.value!!, 1, 0, 0, 0))){
//                    if(it.getDateForSelect()){
//                        (requireActivity().supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0) as CalendarFragment).closeCalendar()
//                    }
//                    else{
//                        /*******  invalidate *******/
//                        binding.calendarLongView.children.forEach {it as DayItemLongView
//                            it.setDateForSelect(false)
//                        }
//
//                        it.setDateForSelect(true)
//                        binding.calendarLongView.children.forEach {it as DayItemLongView
//                            it.invalidate()
//                        }
//
//                        viewModel.year.value = year
//                        viewModel.month.value = month
//                        viewModel.day.value = day
//
//                    }
//                    if(!viewModel.flagForSelected.value!!)viewModel.flagForSelected.value = true
//                }
//            }
//        }
//    }
//


}