package com.gotz.presentation.view.home

import android.util.Log
import com.gotz.presentation.R
import com.gotz.base.BaseFragment
import com.gotz.presentation.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun initFragment() {

    }

    override fun initView() {
        binding.run{
            Log.e("tlHome", "measured ${tlHome.measuredWidth} /// ${tlHome.measuredHeight}")
            Log.e("tlHome", "${tlHome.width} /// ${tlHome.height}")
            Log.e("tlHome", "min ${tlHome.minWidth} /// ${tlHome.minHeight}")
            Log.e("tlHome", "max ${tlHome.maxWidth} /// ${tlHome.maxHeight}")
            Log.e("tlHome", "max ${tlHome.layoutParams.width} /// ${tlHome.layoutParams.height}")
            //tlHome.initLayout(listOf("용산구", "직장동료", "미팅"))
            tlHome.run{
                setData(listOf("용산구", "직장동료", "미팅", "미팅", "용산구", "미팅", "직장동료", "용산구"))
            }
        }
    }

    companion object{
        fun newInstance() = HomeFragment()
    }
}