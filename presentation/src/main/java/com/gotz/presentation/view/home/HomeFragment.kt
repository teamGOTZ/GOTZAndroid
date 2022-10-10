package com.gotz.presentation.view.home

import com.gotz.presentation.R
import com.gotz.base.BaseFragment
import com.gotz.presentation.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun initFragment() {

    }

    companion object{
        fun newInstance() = HomeFragment()
    }
}