package com.gotz.presentation.view.main

import com.gotz.presentation.R
import com.gotz.presentation.base.BaseActivity
import com.gotz.presentation.databinding.ActivityMainBinding
import com.gotz.presentation.view.main.MainViewPagerAdapter.Companion.INDEX_CALENDAR
import com.gotz.presentation.view.main.MainViewPagerAdapter.Companion.INDEX_HOME

class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewPagerAdapter = MainViewPagerAdapter(this)
    override fun onCreate() {

    }

    override fun initView() {
        binding.vpMain.adapter = mainViewPagerAdapter

        binding.bnvMain.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.btn_calendar ->{
                    binding.vpMain.currentItem = INDEX_CALENDAR
                }
                R.id.btn_test ->{
                    binding.vpMain.currentItem = INDEX_HOME
                }
            }
            true
        }
    }
}