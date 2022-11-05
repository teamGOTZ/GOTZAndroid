package com.gotz.base.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.gotz.base.BaseConstraintLayout
import com.gotz.base.R
import com.gotz.base.databinding.LayoutOneDepthTitleBinding

class OneDepthTitleLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseConstraintLayout<LayoutOneDepthTitleBinding>(R.layout.layout_one_depth_title, context, attrs) {

    val textview: TextView = binding.textview

}