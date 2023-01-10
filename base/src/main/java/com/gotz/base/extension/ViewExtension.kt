package com.gotz.base.extension

import android.content.res.ColorStateList
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import com.gotz.base.R
import com.gotz.base.util.OnSingleClickListener

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun View.clickable(){
    isClickable = true
}

fun View.clickableNot(){
    isClickable = false
}

fun View.setOnSingleClickListener(block: (View) -> Unit) {
    val popupClickListener = OnSingleClickListener {
        block(it)
    }
    setOnClickListener(popupClickListener)
}

fun ImageView.disable() {
    backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.Gray_400, null))
}

fun ImageView.enable() {
    backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.Primary, null))
}