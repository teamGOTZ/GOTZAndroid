package com.gotz.presentation.view.calendar.schedule

import android.content.Intent
import android.widget.TimePicker
import androidx.core.view.isGone
import com.gotz.base.BaseActivity
import com.gotz.base.extension.*
import com.gotz.base.util.StringUtil.getStrDate
import com.gotz.base.util.StringUtil.getStrTime
import com.gotz.domain.model.Schedule
import com.gotz.presentation.R
import com.gotz.presentation.databinding.ActivityScheduleAddBinding
import com.gotz.presentation.receiver.ScheduleChangeReceiver
import com.gotz.presentation.view.calendar.calendar.CalendarFragment.Companion.DATE_MILLIS
import com.gotz.presentation.view.calendar.schedule.ScheduleShowActivity.Companion.EXTRA_SCHEDULE
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleAddActivity: BaseActivity<ActivityScheduleAddBinding>(R.layout.activity_schedule_add) {

    private val now: DateTime = DateTime.now()

    var year: Int = now.year
    var month: Int = now.monthOfYear
    var day: Int = now.dayOfMonth

    var schedule: Schedule? = null

    private val scheduleViewModel: ScheduleViewModel by viewModel()

    override fun initActivity() {
        intent.run {
            schedule = getSerializableExtra(EXTRA_SCHEDULE) as Schedule?
            if (schedule == null) {
                val dateTime = DateTime(getLongExtra(DATE_MILLIS, DateTime.now().millis))
                year = dateTime.year
                month = dateTime.monthOfYear
                day = dateTime.dayOfMonth
            }
            else {
                schedule!!.let {
                    year = DateTime(it.start).year
                    month = DateTime(it.start).monthOfYear
                    day = DateTime(it.start).dayOfMonth
                }
            }

        }
    }

    override fun initView() {
        binding.run{
            cvCalendarAddSchedule.gone()

            btnCalendarAddScheduleBack.setOnClickListener {
                //setResult(-1)
                finish()
            }

            swCalendarAddScheduleAllday.setOnCheckedChangeListener { _, isChecked ->
                if(!isChecked){
                    llCalendarAddScheduleSettime.visible()
                }else{
                    llCalendarAddScheduleSettime.gone()
                    tpCalendarAddScheduleStart.gone()
                    tpCalendarAddScheduleEnd.gone()
                }
            }

            llCalendarAddScheduleDate.setOnClickListener {
                tpCalendarAddScheduleStart.gone()
                tpCalendarAddScheduleEnd.gone()

                if(cvCalendarAddSchedule.isGone){
                    cvCalendarAddSchedule.visible()
                }
                else{
                    cvCalendarAddSchedule.gone()
                }
            }

            cvCalendarAddSchedule.setOnDateChangeListener { _, year, month, day ->
                tvCalendarAddScheduleDate.text = getStrDate(year, month +1, day)
                this@ScheduleAddActivity.year = year
                this@ScheduleAddActivity.month = month +1
                this@ScheduleAddActivity.day = day
            }

            tpCalendarAddScheduleStart.setOnTimeChangedListener { _, hour, minute ->
                tvCalendarAddScheduleTimeStart.text = getStrTime(hour, minute)

                val startTime = getMillisForTime(hour, minute)
                val endTime = getMillisForTimePicker(tpCalendarAddScheduleEnd)
                if(startTime > endTime){
                    tpCalendarAddScheduleEnd.hour = hour
                    tpCalendarAddScheduleEnd.minute = minute
                }
            }

            tpCalendarAddScheduleEnd.setOnTimeChangedListener { _, hour, minute ->
                tvCalendarAddScheduleTimeEnd.text = getStrTime(hour, minute)

                val startTime = getMillisForTimePicker(tpCalendarAddScheduleStart)
                val endTime = getMillisForTime(hour, minute)
                if(startTime > endTime){
                    tpCalendarAddScheduleStart.hour = hour
                    tpCalendarAddScheduleStart.minute = minute
                }
            }

            tvCalendarAddScheduleTimeStart.setOnClickListener {
                cvCalendarAddSchedule.gone()
                tpCalendarAddScheduleEnd.gone()

                if(tpCalendarAddScheduleStart.isGone){
                    tpCalendarAddScheduleStart.visible()
                    llCalendarAddScheduleTime.setStart()
                }
                else{
                    tpCalendarAddScheduleStart.gone()
                }
            }

            tvCalendarAddScheduleTimeEnd.setOnClickListener {
                cvCalendarAddSchedule.gone()
                tpCalendarAddScheduleStart.gone()

                if(tpCalendarAddScheduleEnd.isGone){
                    tpCalendarAddScheduleEnd.visible()
                    llCalendarAddScheduleTime.setEnd()
                }
                else{
                    tpCalendarAddScheduleEnd.gone()
                }
            }

            tvCalendarAddScheduleSave.setOnClickListener {
                if(schedule == null) {
                    scheduleViewModel.createSchedule(
                        Schedule.makeSchedule(
                            dateId = getStrForDate(year, month, day),
                            title = etCalendarAddScheduleTitle.text.value ?:"",
                            content = etCalendarAddScheduleContent.text.toString(),
                            isAllDay = swCalendarAddScheduleAllday.isChecked,
                            start = getMillisForTimePicker(tpCalendarAddScheduleStart),
                            end = getMillisForTimePicker(tpCalendarAddScheduleEnd)
                        )
                    )
                    sendBroadcast(Intent(ScheduleChangeReceiver.ACTION_SCHEDULE_ADD))
                }
                else {
                    scheduleViewModel.updateSchedule(
                        Schedule.makeSchedule(
                            scheduleId = schedule!!.scheduleId,
                            dateId = getStrForDate(year, month, day),
                            title = etCalendarAddScheduleTitle.text.value ?:"",
                            content = etCalendarAddScheduleContent.text.toString(),
                            isAllDay = swCalendarAddScheduleAllday.isChecked,
                            start = getMillisForTimePicker(tpCalendarAddScheduleStart),
                            end = getMillisForTimePicker(tpCalendarAddScheduleEnd)
                        )
                    )
                    sendBroadcast(Intent(ScheduleChangeReceiver.ACTION_SCHEDULE_UPDATE))
                }
                finish()
            }
        }
    }

    override fun initData() {
        binding.run{
            if(schedule == null){
                tvCalendarAddScheduleDate.text = getStrDate(year, month, day)

                tvCalendarAddScheduleTimeStart.text = getStrTime(now)
                tvCalendarAddScheduleTimeEnd.text = getStrTime(now)
            }
            else {
                schedule?.let{ schedule ->
                    tvCalendarAddScheduleDate.text = getStrDate(year, month, day)

                    tvCalendarAddScheduleTimeStart.text = getStrTime(DateTime(schedule.start))
                    tvCalendarAddScheduleTimeEnd.text = getStrTime(DateTime(schedule.end))

                    etCalendarAddScheduleTitle.editText.setText(schedule.title)
                    etCalendarAddScheduleContent.setText(schedule.content)

                    swCalendarAddScheduleAllday.isChecked = schedule.isAllDay
                }

            }
        }
    }

    override fun initObserver() {
        binding.run{
            etCalendarAddScheduleTitle.text.observe(this@ScheduleAddActivity) { text ->
                if (text.isNotEmpty()){
                    tvCalendarAddScheduleSave.clickable()
                    tvCalendarAddScheduleSave.setTextColor(resources.getColor(R.color.Primary, null))
                }
                else{
                    tvCalendarAddScheduleSave.clickableNot()
                    tvCalendarAddScheduleSave.setTextColor(resources.getColor(R.color.Gray_400, null))
                }
            }
        }

    }

    private fun getMillisForTime(hours: Int, minutes: Int): Long {
        return DateTime(year, month, day, hours, minutes, 0).millis
    }

    private fun getMillisForTimePicker(timePicker: TimePicker): Long {
        return DateTime(year, month, day, timePicker.hour, timePicker.minute, 0).millis
    }

    private fun getStrForDate(year: Int, month: Int, day: Int): String =
        DateTime(year, month, day, 0, 0, 0 ).toString("yyyy-MM-dd")
}