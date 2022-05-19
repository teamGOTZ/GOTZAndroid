package com.wedid.views.calendar.memo

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.wedid.R
import com.wedid.base.BaseActivity
import com.wedid.base.BaseApplication
import com.wedid.databinding.ActivityCalendarMemoBinding
import com.wedid.util.CalendarUtil.Companion.charDayOfWeek
import org.joda.time.DateTime

class CalendarMemoActivity: BaseActivity<ActivityCalendarMemoBinding>() {
    override val layoutResID: Int get() = R.layout.activity_calendar_memo

    val uid: Int get() = intent.getIntExtra("UID", -1)
    val date: Long get() = intent.getLongExtra("DATE", 0L)

    private val RESULT_SAVE = 1

    private val viewModel: CalendarMemoViewModel by viewModels{
        CalendarMemoViewModelFactory((application as BaseApplication).roomCalendarMemoRepositoryImpl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewmodel = viewModel

        initView()
        if(uid==-1) isNew()
        else isModify()

        initObserver()
        btnClick()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val focusView = currentFocus
        if (focusView != null && ev != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()

            if (!rect.contains(x, y)) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isModify(){
        viewModel.updateData(uid)
        viewModel.uidRoomCalendarMemo.observe(this, Observer {
        val start: DateTime = DateTime(it.start)
        val end: DateTime = DateTime(it.end)
            viewModel.year.value = start.year
            viewModel.month.value = start.monthOfYear
            viewModel.day.value = start.dayOfMonth
            viewModel.startTime.value = (start.hourOfDay*100) + (start.minuteOfHour)
            viewModel.endTime.value = (end.hourOfDay*100) + (end.minuteOfHour)

            setDateText(start.year, start.monthOfYear-1, start.dayOfMonth)
            setTimeText(start.hourOfDay, start.minuteOfHour, true)
            setTimeText(end.hourOfDay, end.minuteOfHour, false)

            binding.tpCalendarMemoStart.hour = start.hourOfDay
            binding.tpCalendarMemoStart.minute = start.minuteOfHour
            binding.tpCalendarMemoEnd.hour = end.hourOfDay
            binding.tpCalendarMemoEnd.minute = end.minuteOfHour

            binding.cvCalendarMemo.date = start.millis
            binding.cvCalendarMemo.setOnDateChangeListener { calendarView, year, month, day ->
                viewModel.year.value = year
                viewModel.month.value = month+1
                viewModel.day.value = day
                setDateText(year, month, day)

                binding.cvCalendarMemo.visibility = View.GONE
            }

            binding.tpCalendarMemoStart.setOnTimeChangedListener { timePicker, hour, minute ->
                viewModel.startTime.value = (hour*100) + minute
                setTimeText(hour, minute, true)
                if(viewModel.endTime.value!! < viewModel.startTime.value!!){
                    binding.tpCalendarMemoEnd.hour = hour
                    binding.tpCalendarMemoEnd.minute = minute
                    viewModel.endTime.value = (hour*100) + minute
                }
            }

            binding.tpCalendarMemoEnd.setOnTimeChangedListener { timePicker, hour, minute ->
                viewModel.endTime.value = (hour*100) + minute
                setTimeText(hour, minute, false)
                if(viewModel.endTime.value!! < viewModel.startTime.value!!){
                    binding.tpCalendarMemoStart.hour = hour
                    binding.tpCalendarMemoStart.minute = minute
                    viewModel.startTime.value = (hour*100) + minute
                }
            }

            binding.etCalendarMemoTitle.custom_edittext_et.setText(it.title)
            binding.etCalendarMemoContent.setText(it.content)

            binding.swCalendarMemoAllday.isChecked = it.isAllDay

            viewModel.uid.value = uid
        })
    }

    private fun isNew(){
        val now: DateTime = DateTime.now().plusHours(1)

        if(date == 0L){
            binding.cvCalendarMemo.date = DateTime.now().millis
            setDateText(now.year, now.monthOfYear-1, now.dayOfMonth)

            viewModel.year.value = now.year
            viewModel.month.value = now.monthOfYear
            viewModel.day.value = now.dayOfMonth
            viewModel.startTime.value = (now.hourOfDay*100)
            viewModel.endTime.value = (now.hourOfDay*100)

            setTimeText(now.hourOfDay, 0, true)
            setTimeText(now.hourOfDay, 0, false)

            binding.tpCalendarMemoStart.hour = now.hourOfDay
            binding.tpCalendarMemoStart.minute = 0
            binding.tpCalendarMemoEnd.hour = now.hourOfDay
            binding.tpCalendarMemoEnd.minute = 0
        }
        else{
            val dateTime = DateTime(date)
            binding.cvCalendarMemo.date = date
            setDateText(DateTime(date).year, DateTime(date).monthOfYear-1, DateTime(date).dayOfMonth)

            viewModel.year.value = dateTime.year
            viewModel.month.value = dateTime.monthOfYear
            viewModel.day.value = dateTime.dayOfMonth
            viewModel.startTime.value = (dateTime.hourOfDay*100)
            viewModel.endTime.value = (dateTime.hourOfDay*100)

            setTimeText(dateTime.hourOfDay, 0, true)
            setTimeText(dateTime.hourOfDay, 0, false)

            binding.tpCalendarMemoStart.hour = dateTime.hourOfDay
            binding.tpCalendarMemoStart.minute = 0
            binding.tpCalendarMemoEnd.hour = dateTime.hourOfDay
            binding.tpCalendarMemoEnd.minute = 0
        }

        binding.cvCalendarMemo.setOnDateChangeListener { calendarView, year, month, day ->
            viewModel.year.value = year
            viewModel.month.value = month+1
            viewModel.day.value = day
            setDateText(year, month, day)

            binding.cvCalendarMemo.visibility = View.GONE
        }

        binding.tpCalendarMemoStart.setOnTimeChangedListener { timePicker, hour, minute ->
            viewModel.startTime.value = (hour*100) + minute
            setTimeText(hour, minute, true)
            if(viewModel.endTime.value!! < viewModel.startTime.value!!){
                binding.tpCalendarMemoEnd.hour = hour
                binding.tpCalendarMemoEnd.minute = minute
                viewModel.endTime.value = (hour*100) + minute
            }
        }

        binding.tpCalendarMemoEnd.setOnTimeChangedListener { timePicker, hour, minute ->
            viewModel.endTime.value = (hour*100) + minute
            setTimeText(hour, minute, false)
            if(viewModel.endTime.value!! < viewModel.startTime.value!!){
                binding.tpCalendarMemoStart.hour = hour
                binding.tpCalendarMemoStart.minute = minute
                viewModel.startTime.value = (hour*100) + minute
            }
        }
    }


    override fun initView() {
        binding.cvCalendarMemo.visibility = View.GONE
        binding.tpCalendarMemoStart.visibility = View.GONE
        binding.tpCalendarMemoEnd.visibility = View.GONE
    }

    private fun setDateText(year: Int, month: Int, day: Int){
        var str = year.toString() + "년 " + (month+1).toString() + "월 " + day.toString() + "일 (" + charDayOfWeek(DateTime(year, month+1, day, 0, 0, 0).dayOfWeek) + ")"
        binding.tvCalendarMemoDate.setText(str)
    }

    private fun setTimeText(hour: Int, minute: Int, flag: Boolean){
        var str: String = ""
        if(hour > 11){
            str += "오후 "
            if(hour == 12) str += hour.toString() + ":"
            else str += (hour%12).toString() + ":"

            if(minute < 10) str += "0" + minute.toString()
            else str += minute.toString()
        }
        else{
            str += "오전 "
            if(hour == 0) str += "12:"
            else str += hour.toString() + ":"

            if(minute < 10) str += "0" + minute.toString()
            else str += minute.toString()
        }
        if(flag){ // startTime
            binding.tvCalendarMemoTimeStart.text = str
        }
        else{ // endTime
            binding.tvCalendarMemoTimeEnd.text = str
        }

    }

    override fun initObserver() {
        binding.etCalendarMemoTitle.text.observe(this, Observer {
            if(it.length > 0) binding.btnCalendarMemoSave.isEnabled = true
            else binding.btnCalendarMemoSave.isEnabled = false
            viewModel.title.value = it
        })
    }

    override fun btnClick() {

        viewModel.saveButtonEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                setResult(RESULT_SAVE)
                finish()
            }
        })

        binding.btnCalendarMemoBack.setOnClickListener {
            setResult(-1)
            finish()
        }

        binding.swCalendarMemoAllday.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isAllDay.value = isChecked
            if(!isChecked){
                binding.llCalendarMemoSettime.visibility = View.VISIBLE
            }else{
                binding.llCalendarMemoSettime.visibility = View.GONE
                binding.tpCalendarMemoStart.visibility = View.GONE
                binding.tpCalendarMemoEnd.visibility = View.GONE
            }
        }

        binding.llCalendarMemoDate.setOnClickListener {
            binding.tpCalendarMemoStart.visibility = View.GONE
            binding.tpCalendarMemoEnd.visibility = View.GONE

            if(binding.cvCalendarMemo.visibility == View.GONE){
                binding.cvCalendarMemo.visibility = View.VISIBLE
            }
            else{
                binding.cvCalendarMemo.visibility = View.GONE
            }
        }

        binding.tvCalendarMemoTimeStart.setOnClickListener {
            binding.cvCalendarMemo.visibility = View.GONE
            binding.tpCalendarMemoEnd.visibility = View.GONE

            if(binding.tpCalendarMemoStart.visibility == View.GONE){
                binding.tpCalendarMemoStart.visibility = View.VISIBLE
                binding.llCalendarMemoTime.setStart()
            }
            else{
                binding.tpCalendarMemoStart.visibility = View.GONE
            }
        }

        binding.tvCalendarMemoTimeEnd.setOnClickListener {
            binding.cvCalendarMemo.visibility = View.GONE
            binding.tpCalendarMemoStart.visibility = View.GONE

            if(binding.tpCalendarMemoEnd.visibility == View.GONE){
                binding.tpCalendarMemoEnd.visibility = View.VISIBLE
                binding.llCalendarMemoTime.setEnd()
            }
            else{
                binding.tpCalendarMemoEnd.visibility = View.GONE
            }
        }
    }
}