package com.gotz.presentation.view.main

import com.gotz.presentation.R
import com.gotz.base.BaseActivity
import com.gotz.presentation.databinding.ActivityMainBinding
import com.gotz.presentation.view.main.MainViewPagerAdapter.Companion.INDEX_CALENDAR
import com.gotz.presentation.view.main.MainViewPagerAdapter.Companion.INDEX_HOME
import com.gotz.presentation.view.main.MainViewPagerAdapter.Companion.INDEX_MY

class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewPagerAdapter = MainViewPagerAdapter(this)
    override fun initActivity() {

    }

    override fun initView() {
        binding.vpMain.adapter = mainViewPagerAdapter

        binding.bnvMain.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.btn_home ->{
                    binding.vpMain.currentItem = INDEX_HOME
                }
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