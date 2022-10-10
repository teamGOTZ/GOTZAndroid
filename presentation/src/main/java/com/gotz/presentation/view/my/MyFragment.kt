package com.gotz.presentation.view.my

import com.gotz.presentation.R
import com.gotz.base.BaseFragment
import com.gotz.presentation.databinding.FragmentMyBinding

class MyFragment: BaseFragment<FragmentMyBinding>(R.layout.fragment_my)  {
    override fun initFragment() {

    }

    companion object{
        fun newInstance() = MyFragment()
    }
}