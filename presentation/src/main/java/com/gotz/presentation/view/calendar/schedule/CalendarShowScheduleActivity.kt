package com.gotz.presentation.view.calendar.schedule

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MenuRes
import com.gotz.base.BaseActivity
import com.gotz.base.extension.gone
import com.gotz.base.util.StringUtil.getStrDate
import com.gotz.base.util.StringUtil.getStrTime2
import com.gotz.base.util.StringUtil.getStrTime3
import com.gotz.domain.model.Schedule
import com.gotz.presentation.R
import com.gotz.presentation.databinding.ActivityCalendarShowScheduleBinding
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarShowScheduleActivity: BaseActivity<ActivityCalendarShowScheduleBinding>(R.layout.activity_calendar_show_schedule) {

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
        "${getStrTime2(DateTime(start))}${getStrTime3(DateTime(start))} - ${getStrTime2(DateTime(end))}${getStrTime3(DateTime(end))}"

    private fun showMenu(view: View, @MenuRes menuRes: Int){
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(menuRes,popup.menu)

        popup.setOnMenuItemClickListener{
            if(it.itemId == R.id.option_modify){    // 수정
                val intent = Intent(this, CalendarAddScheduleActivity::class.java).apply {
                    putExtra(EXTRA_SCHEDULE, schedule)
                }
                startActivity(intent)
                finish()
            }

            if(it.itemId == R.id.option_delete){    // 삭제
                val dlg : AlertDialog.Builder = AlertDialog.Builder(this)
                dlg.setMessage("일정을 삭제하시겠습니까?")
                dlg.setPositiveButton("삭제", DialogInterface.OnClickListener{ _, _ ->
                    scheduleViewModel.deleteSchedule(schedule)
                    finish()
                })
                dlg.setNegativeButton("취소", DialogInterface.OnClickListener { _, _ ->

                })
                dlg.show()
            }
            false
        }

        popup.setOnDismissListener {

        }

        popup.show()
    }
//
//    override fun onResume() {
//        super.onResume()
//        // uid로 데이터 호출 후 갱신
//        initView()
//    }
//
//    private fun showMenu(v: View, @MenuRes menuRes: Int){
//        val popup = PopupMenu(this, v)
//        popup.menuInflater.inflate(menuRes,popup.menu)
//
//        popup.setOnMenuItemClickListener{
//            if(it.itemId == R.id.option_modify){    // 수정
//                openActivityForResult(CalendarMemoActivity(), uid)
//            }
//
//            if(it.itemId == R.id.option_delete){    // 삭제
//                val dlg : AlertDialog.Builder = AlertDialog.Builder(this)
//                dlg.setMessage("일정을 삭제하시겠습니까?")
//                dlg.setPositiveButton("삭제", DialogInterface.OnClickListener{ dialog, which ->
//                    viewModel.delete(uid)
//                    setResult(RESULT_DELETE)
//                    finish()
//                })
//                dlg.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
//
//                })
//                dlg.show()
//            }
//            false
//        }
//
//        popup.setOnDismissListener {
//
//        }
//
//        popup.show()
//    }
//
//    fun openActivityForResult(activity: Activity, uid: Int){
//        val intent = Intent(this, activity::class.java)
//        intent.putExtra("UID", uid)
//        startForResult.launch(intent)
//    }
//
//    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//        setResult(it.resultCode)
//        finish()
//    }
}