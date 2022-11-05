package com.gotz.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable


abstract class BaseFragment<T: ViewDataBinding>(@LayoutRes val layoutResID: Int) :Fragment(){
    lateinit var binding: T

    val compositeDisposable = CompositeDisposable()

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        return binding.root
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        initFragment()
        initView()
    }

    abstract fun initFragment()

    open fun initView(){}

    open fun btnClick(){ }

    open fun initObserver(){ }
}