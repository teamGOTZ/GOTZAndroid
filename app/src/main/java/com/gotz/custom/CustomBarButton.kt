package com.gotz.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gotz.R

class CustomBarButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        val view = LayoutInflater.from(getContext()).inflate(R.layout.custom_bar_button, this, false)
        addView(view)

        val custom_bar_button_iv = findViewById<ImageView>(R.id.custom_bar_button_iv)
        val custom_bar_button_tv = findViewById<TextView>(R.id.custom_bar_button_tv)

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomBarButton, defStyleAttr, 0)

        val image = typeArray.getResourceId(R.styleable.CustomBarButton_img, R.drawable.ic_launcher_background)
        custom_bar_button_iv.setBackgroundResource(image)
        val text = typeArray.getText(R.styleable.CustomBarButton_text)
        custom_bar_button_tv.text = text

        typeArray.recycle()
    }

}