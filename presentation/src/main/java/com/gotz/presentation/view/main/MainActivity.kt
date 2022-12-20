package com.gotz.presentation.view.main

import com.gotz.presentation.R
import com.gotz.base.BaseActivity
import com.gotz.presentation.databinding.ActivityMainBinding
import com.gotz.presentation.view.calendar.calendar.CalendarViewModel
import com.gotz.presentation.view.main.MainViewPagerAdapter.Companion.INDEX_CALENDAR
import com.gotz.presentation.view.main.MainViewPagerAdapter.Companion.INDEX_MY
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val calendarViewModel: CalendarViewModel by viewModel()



    private val mainViewPagerAdapter = MainViewPagerAdapter(this)
    override fun initActivity() {

    }

    override fun initView() {
        binding.run{
            vpMain.run {
                adapter = mainViewPagerAdapter
                isUserInputEnabled = false
            }

            bnvMain.setOnItemSelectedListener { item ->
                when(item.itemId){
//                    R.id.btn_home ->{
//                        binding.vpMain.currentItem = INDEX_HOME
//                    }
                    R.id.btn_calendar ->{
                        binding.vpMain.currentItem = INDEX_CALENDAR
                    }
                    R.id.btn_my ->{
                        binding.vpMain.currentItem = INDEX_MY
                    }
                }
                true
            }

        }
    }

}