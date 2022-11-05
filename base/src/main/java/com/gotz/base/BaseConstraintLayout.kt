package com.gotz.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

@SuppressLint("ViewConstructor")
open class BaseConstraintLayout<T: ViewDataBinding>(
    @LayoutRes layoutResId: Int,
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    var binding: T

    init{
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutResId, this, false)
        this.addView(binding.root)
    }

}