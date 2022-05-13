package com.wedid.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.wedid.R

class CustomTodayButton@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        val view = LayoutInflater.from(getContext()).inflate(R.layout.custom_today_button, this, false)
        addView(view)

    }
}