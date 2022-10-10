package com.gotz.presentation.view.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gotz.presentation.view.calendar.base.CalendarFragment
import com.gotz.presentation.view.home.HomeFragment
import com.gotz.presentation.view.my.MyFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    companion object{
        const val INDEX_HOME = 0
        const val INDEX_CALENDAR = 1
        const val INDEX_MY = 2
        const val TAB_COUNT = 4

    }
    override fun getItemCount(): Int = TAB_COUNT

    override fun createFragment(position: Int): Fragment {
        return when(position){
            INDEX_HOME -> {
                HomeFragment.newInstance()
            }
            INDEX_CALENDAR -> {
                CalendarFragment.newInstance()
            }
            else -> {
                MyFragment.newInstance()
            }
        }
    }

}