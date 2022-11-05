package com.gotz.presentation.view.home

import android.util.Log
import androidx.core.view.children
import com.gotz.presentation.R
import com.gotz.base.BaseFragment
import com.gotz.presentation.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun initFragment() {

    }

    override fun initView() {
        binding.run{
            tlHome.initLayout(listOf("용산구", "직장동료", "미팅"))
        }
    }

    companion object{
        fun newInstance() = HomeFragment()
    }
}