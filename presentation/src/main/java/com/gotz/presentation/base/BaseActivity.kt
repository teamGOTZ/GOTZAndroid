package com.gotz.presentation.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T: ViewDataBinding>(@LayoutRes val layoutResID: Int) : AppCompatActivity() {
    lateinit var binding: T

     final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         binding = DataBindingUtil.setContentView(this, layoutResID)
         binding.lifecycleOwner = this
         getSystemService(Context.INPUT_METHOD_SERVICE)

         onCreate()
         initObserver()
         initView()
         initIntent()
    }

    open fun onCreate(){}

    open fun initObserver(){}

    open fun initView(){}

    open fun initIntent(){}

    open fun btnClick(){}

    protected fun makeToast(str:String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
}