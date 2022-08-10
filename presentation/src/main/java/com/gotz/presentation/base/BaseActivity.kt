package com.gotz.presentation.base

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
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
}