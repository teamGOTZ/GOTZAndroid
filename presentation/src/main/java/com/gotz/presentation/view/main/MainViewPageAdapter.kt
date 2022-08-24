package com.gotz.presentation.view.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gotz.presentation.view.calendar.CalendarFragment
import com.gotz.presentation.view.home.HomeFragment

class MainViewPageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    companion object{
        const val INDEX_CALENDAR = 0
        const val INDEX_HOME = 1
        const val TAB_COUNT = 4

    }
    override fun getItemCount(): Int = TAB_COUNT

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                CalendarFragment.newInstance()
            }
            else -> {
                HomeFragment.newInstance()
            }
        }
    }

}