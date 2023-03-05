package com.gotz.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.gotz.presentation.R
import com.gotz.presentation.databinding.LayoutOneDepthTitleBinding

class OneDepthTitleLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : com.gotz.presentation.base.BaseConstraintLayout<LayoutOneDepthTitleBinding>(R.layout.layout_one_depth_title, context, attrs) {

    val textview: TextView = binding.textview

}