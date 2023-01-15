package com.gotz.presentation.view.calendar.schedule

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import com.gotz.base.BaseActivity
import com.gotz.base.extension.gone
import com.gotz.base.util.StringUtil.getStrDate
import com.gotz.base.util.StringUtil.getStrTime2
import com.gotz.base.util.StringUtil.getAmPm
import com.gotz.domain.model.Schedule
import com.gotz.presentation.R
import com.gotz.presentation.databinding.ActivityScheduleShowBinding
import com.gotz.presentation.receiver.ScheduleChangeReceiver
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleShowActivity: BaseActivity<ActivityScheduleShowBinding>(R.layout.activity_schedule_show) {

    private val scheduleViewModel: ScheduleViewModel by viewModel()

    private lateinit var schedule: Schedule
    companion object {
        const val EXTRA_SCHEDULE = "schedule"
    }

    override fun initActivity() {
        intent.run {
            schedule = getSerializableExtra(EXTRA_SCHEDULE) as Schedule
        }
    }

    override fun initView() {
        binding.run {
            tvCalendarShowScheduleTitle.text = schedule.title
            tvCalendarShowScheduleContent.text = schedule.content
            tvCalendarShowScheduleDate.text = getStrDate(DateTime(schedule.start))
            if(schedule.isAllDay) {
                tvCalendarShowScheduleTime.gone()
                ivCalendarShowScheduleTime.gone()
            }
            else {
                tvCalendarShowScheduleTime.text = getTimeStartEnd(schedule.start, schedule.end)
            }

            btnCalendarShowScheduleMenu.setOnClickListener {
                showMenu(it, R.menu.menu_schedule)
            }

            btnCalendarShowScheduleBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun getTimeStartEnd(start: Long, end: Long) =
        "${getStrTime2(DateTime(start))}${getAmPm(DateTime(start))} - ${getStrTime2(DateTime(end))}${getAmPm(DateTime(end))}"

    private fun showMenu(view: View, @MenuRes menuRes: Int){
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(menuRes,popup.menu)

        popup.setOnMenuItemClickListener{
            if(it.itemId == R.id.option_modify){    // 수정
                val intent = Intent(this, ScheduleAddActivity::class.java).apply {
                    putExtra(EXTRA_SCHEDULE, schedule)
                }
                startActivity(intent)
                finish()
            }

            if(it.itemId == R.id.option_delete){    // 삭제
                val dlg : AlertDialog.Builder = AlertDialog.Builder(this).apply {
                    setMessage("일정을 삭제하시겠습니까?")

                    setPositiveButton("삭제") { _, _ ->
                        sendBroadcast(Intent(ScheduleChangeReceiver.ACTION_SCHEDULE_DELETE))
                        scheduleViewModel.deleteSchedule(schedule)
                        finish()
                    }

                    setNegativeButton("취소") { _, _ ->

                    }
                }

                dlg.show()
            }
            false
        }

        popup.setOnDismissListener {

        }

        popup.show()
    }
}