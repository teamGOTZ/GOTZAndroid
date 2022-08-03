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
         initView()
         initIntent()
         initObserver()
    }

    open fun onCreate(){}

    open fun btnClick(){}

    open fun initObserver(){}

    open fun initView(){}

    open fun initIntent(){}

    protected fun makeToast(str:String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
}