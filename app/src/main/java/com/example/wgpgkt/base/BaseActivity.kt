package com.example.wgpgkt.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T: ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: T

    abstract val layoutResID: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResID)
        binding.lifecycleOwner = this
    }

    open fun btnClick(){}

    protected fun makeToast(str:String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
}