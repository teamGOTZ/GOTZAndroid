package com.wedid.views.calendar.memo

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.MenuRes
import androidx.lifecycle.Observer
import com.wedid.R
import com.wedid.base.BaseActivity
import com.wedid.base.BaseApplication
import com.wedid.databinding.ActivityCalendarMemoDetailBinding
import com.wedid.util.CalendarUtil.Companion.getStrDate
import com.wedid.util.CalendarUtil.Companion.getStrTime
import org.joda.time.DateTime
import java.lang.Exception

class CalendarMemoDetailActivity: BaseActivity<ActivityCalendarMemoDetailBinding>() {
    override val layoutResID: Int get() = R.layout.activity_calendar_memo_detail

    // uid 통한 데이터로 변경 intent 값 제거해주고 -> intent로는 uid 값만 넘김
    val uid: Int get() = intent.getIntExtra("UID", -1)

    private val RESULT_SAVE = 1
    private val RESULT_DELETE = 2

    private val viewModel: CalendarMemoDetailViewModel by viewModels{
        CalendarMemoDetailViewModelFactory((application as BaseApplication).roomCalendarMemoRepositoryImpl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewmodel = viewModel
        initView()
        btnClick()
    }

    override fun initView() {
        viewModel.updateData(uid)
        viewModel.uidRoomCalendarMemo.observe(this, Observer {
            try{
                viewModel.title.value = it.title
                viewModel.content.value = it.content
                viewModel.isAllDay.value = it.isAllDay
                viewModel.start.value = it.start
                viewModel.end.value = it.end

                binding.tvCalendarMemoDetailTitle.text = viewModel.title.value
                binding.tvCalendarMemoDetailDate.text = getStrDate(DateTime(viewModel.start.value))
                if(viewModel.isAllDay.value!!){
                    binding.tvCalendarMemoDetailTime.text = "하루종일"
                }
                else{
                    binding.tvCalendarMemoDetailTime.text = getStrTime(DateTime(viewModel.start.value)) + " - " + getStrTime(DateTime(viewModel.end.value))
                    if(getStrTime(DateTime(viewModel.start.value)).equals(getStrTime(DateTime(viewModel.end.value)))){
                        binding.tvCalendarMemoDetailTime.text = getStrTime(DateTime(viewModel.start.value))
                    }else{
                        binding.tvCalendarMemoDetailTime.text = getStrTime(DateTime(viewModel.start.value)) + " - " + getStrTime(DateTime(viewModel.end.value))
                    }
                }
                binding.tvCalendarMemoDetailContent.text = viewModel.content.value
            }catch (e:Exception){
                Log.e("DELETE", e.message.toString())
            }
        })
    }

    override fun btnClick() {
        binding.btnCalendarMemoDetailMenu.setOnClickListener {
            showMenu(it, R.menu.popup_calendar_memo_detail)
        }

        binding.btnCalendarMemoDetailBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // uid로 데이터 호출 후 갱신
        initView()
    }

    private fun showMenu(v:View, @MenuRes menuRes: Int){
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(menuRes,popup.menu)

        popup.setOnMenuItemClickListener{
            if(it.itemId == R.id.option_modify){    // 수정
                openActivityForResult(CalendarMemoActivity(), uid)
            }

            if(it.itemId == R.id.option_delete){    // 삭제
                val dlg :AlertDialog.Builder = AlertDialog.Builder(this)
                dlg.setMessage("일정을 삭제하시겠습니까?")
                dlg.setPositiveButton("삭제", DialogInterface.OnClickListener{ dialog, which ->
                    viewModel.delete(uid)
                    setResult(RESULT_DELETE)
                    finish()
                })
                dlg.setNegativeButton("취소",DialogInterface.OnClickListener { dialog, which ->

                })
                dlg.show()
            }
            false
        }

        popup.setOnDismissListener {

        }

        popup.show()
    }

    fun openActivityForResult(activity: Activity, uid: Int){
        val intent = Intent(this, activity::class.java)
        intent.putExtra("UID", uid)
        startForResult.launch(intent)
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        setResult(it.resultCode)
        finish()
    }
}