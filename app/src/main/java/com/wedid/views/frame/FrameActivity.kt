package com.wedid.views.frame

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.wedid.R
import com.wedid.base.BaseActivity
import com.wedid.databinding.ActivityFrameBinding
import com.wedid.views.calendar.CalendarFragment
import com.google.android.material.snackbar.Snackbar

class FrameActivity: BaseActivity<ActivityFrameBinding>() {
    override val layoutResID: Int = R.layout.activity_frame

    private val RESULT_SAVE = 1
    private val RESULT_DELETE = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.llAppbar.visibility= View.GONE

        binding.btnFrameCalendarToday.setOnClickListener {
            val fragment = supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0) as CalendarFragment
            fragment.refreshFragment()
        }

        binding.btnFrameCalendarLogo.setOnClickListener {
            val fragment = supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0) as CalendarFragment
            fragment.closeCalendar()
        }
    }

    fun openActivityForResult(activity: Activity, uid: Int, date: Long = 0){
        val intent = Intent(this, activity::class.java)
        intent.putExtra("UID", uid)
        intent.putExtra("DATE", date)
        startForResult.launch(intent)
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_SAVE){
            Snackbar.make(binding.llFrame, "일정이 저장되었습니다.", Snackbar.LENGTH_LONG).show()
        }
        if(it.resultCode == RESULT_DELETE){
            Snackbar.make(binding.llFrame, "일정이 삭제되었습니다.", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun btnClick() {
        binding.btnTest1.setOnClickListener {
            makeToast("BUTTON1")
        }
        binding.btnTest2.setOnClickListener {
            makeToast("BUTTON2")
        }
    }
}