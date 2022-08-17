package com.gotz.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.databinding.ViewDataBinding


abstract class BaseFragment<T: ViewDataBinding>(@LayoutRes val layoutResID: Int) :Fragment(){
    lateinit var binding: T

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        onCreateView()
        return binding.root
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        onViewCreated()
    }

    abstract fun onCreateView()

    abstract fun onViewCreated()

    open fun btnClick(){ }

    open fun initObserver(){ }
}