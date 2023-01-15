package com.gotz.base

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
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

        initActivity()
        initView()
        initNavigation()
        initObserver()
        initData()
        registerReceiver()
        hideNavigationBar()
    }

    override fun onDestroy() {
        unregisterReceiver()
        super.onDestroy()
    }

    abstract fun initActivity()

    open fun initView(){}

    open fun initNavigation(){}

    open fun initObserver(){}

    open fun initData(){}

    open fun registerReceiver(){}

    open fun unregisterReceiver(){}

    final override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val focusView = currentFocus
        if (focusView != null && ev != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()

            if (!rect.contains(x, y)) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideNavigationBar(){
        window?.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }
}