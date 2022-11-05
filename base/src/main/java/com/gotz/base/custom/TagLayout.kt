package com.gotz.base.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.gotz.base.R
import com.gotz.base.util.DimensionUtil.dpToPx

class TagLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var constraintTextView: TextView? = null
    private var constraintLayoutWidth: Int = 0
    private var constraintLayoutHeight: Int = 0
    private var constraintNowWidth: Int = 0

    private val constraintSet: ConstraintSet = ConstraintSet()

    init{
        constraintSet.clone(this)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        constraintLayoutWidth = right - left
        constraintLayoutHeight = bottom - top
        Log.e("onLayout", constraintLayoutWidth.toString())
        Log.e("onLayout", constraintLayoutHeight.toString())
    }

    fun initLayout(lists: List<String>){

//        measure(0,0)
//        constraintLayoutWidth = measuredWidth
//        constraintLayoutHeight = measuredHeight

        lists.map{ data ->
            val textView = TextView(context).apply{
                id = View.generateViewId()
                text = data
                background = resources.getDrawable(R.drawable.style_tag)
                setPadding(dpToPx(context, 6).toInt(),
                    dpToPx(context, 4).toInt(),
                    dpToPx(context, 6).toInt(),
                    dpToPx(context, 4).toInt())
            }

            addView(textView)
            setConstraint(textView)
        }
    }

    private fun setConstraint(textView: TextView){
        if(constraintTextView == null){
            constraintSet.constrainDefaultWidth(textView.id, ConstraintSet.MATCH_CONSTRAINT_WRAP)
            constraintSet.constrainDefaultHeight(textView.id, ConstraintSet.MATCH_CONSTRAINT_WRAP)
            constraintSet.connect(textView.id, ConstraintSet.START, this.id, ConstraintSet.START, 0)
            constraintSet.connect(textView.id, ConstraintSet.TOP, this.id, ConstraintSet.TOP, 0)
            constraintSet.applyTo(this)
        }
        else{
            constraintTextView?.let{ view ->
                constraintSet.constrainDefaultWidth(textView.id, ConstraintSet.MATCH_CONSTRAINT_WRAP)
                constraintSet.constrainDefaultHeight(textView.id, ConstraintSet.MATCH_CONSTRAINT_WRAP)
                constraintSet.connect(textView.id, ConstraintSet.START, view.id, ConstraintSet.END, dpToPx(context, 4).toInt())
                constraintSet.connect(textView.id, ConstraintSet.TOP, view.id, ConstraintSet.TOP, 0)
                constraintSet.applyTo(this)
            }
        }

        constraintNowWidth += getTextViewWidth(textView) + dpToPx(context, 4).toInt()
        constraintLayoutWidth = getLayoutWidth()

        Log.e("TagLayout", "layoutWidth : $constraintLayoutWidth")
        Log.e("TagLayout", "nowWidth : $constraintNowWidth")
        constraintTextView = textView

    }

    override fun addView(child: View?) {
        super.addView(child)
    }

    private fun getWidthStatus(): Int{
        return 0
    }

    private fun getTextViewWidth(textView: TextView): Int{
        textView.measure(0,0)
        return textView.measuredWidth
    }

    private fun getLayoutWidth(): Int{
        measure(0,0)
        return measuredWidth
    }
}