package com.gotz.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.gotz.presentation.R
import com.gotz.presentation.util.DimensionUtil.dpToPx

class TagLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var constraintTextView: TextView? = null
    private var constraintLayoutWidth: Int = 0
    private var constraintLayoutHeight: Int = 0
    private var constraintNowWidth: Int = 0
    private var constraintNowHeight: Int = 0

    private var textViewList = arrayListOf<TextView>()

    private var setOneTimeFlag: Boolean = true

    private val constraintSet: ConstraintSet = ConstraintSet()

    init{
        constraintSet.clone(this)
    }

    companion object{
        private const val STATUS_ERROR = -1
        private const val STATUS_ATTACH_FIRST = 0
        private const val STATUS_ATTACH_SIDE = 1
        private const val STATUS_ATTACH_BOTTOM = 2
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        constraintLayoutWidth = right - left - 10
        constraintLayoutHeight = bottom - top - 10

        if(setOneTimeFlag){
            setOneTimeFlag = false
            setTextViewOnLayout()
        }
    }

    fun setData(lists: List<String>){
        lists.map{ data ->
            val textView = TextView(context).apply{
                id = View.generateViewId()
                text = data
                background = AppCompatResources.getDrawable(context, R.drawable.style_tag)

                setPadding(dpToPx(context, 6).toInt(),
                    dpToPx(context, 4).toInt(),
                    dpToPx(context, 6).toInt(),
                    dpToPx(context, 4).toInt())
            }

            textViewList.add(textView)
        }
    }

    private fun setTextViewOnLayout(){
        textViewList.map { textView ->
            addView(textView)
            setConstraint(textView)
        }
    }

    private fun setConstraint(textView: TextView){
        constraintSet.constrainDefaultWidth(textView.id, ConstraintSet.MATCH_CONSTRAINT_WRAP)
        constraintSet.constrainDefaultHeight(textView.id, ConstraintSet.MATCH_CONSTRAINT_WRAP)

        when(getWidthStatus(textView)){
            STATUS_ATTACH_FIRST -> {
                constraintSet.connect(textView.id, ConstraintSet.START, this.id, ConstraintSet.START, 0)
                constraintSet.connect(textView.id, ConstraintSet.TOP, this.id, ConstraintSet.TOP, 0)
                constraintSet.applyTo(this)
            }
            STATUS_ATTACH_SIDE -> {
                constraintTextView?.let{ view ->
                    constraintSet.connect(textView.id, ConstraintSet.START, view.id, ConstraintSet.END, dpToPx(context, 4).toInt())
                    constraintSet.connect(textView.id, ConstraintSet.TOP, view.id, ConstraintSet.TOP, 0)
                    constraintSet.applyTo(this)
                }
            }
            STATUS_ATTACH_BOTTOM -> {
                constraintNowWidth = 0
                constraintTextView?.let{ view ->
                    constraintSet.connect(textView.id, ConstraintSet.START, this.id, ConstraintSet.START, 0)
                    constraintSet.connect(textView.id, ConstraintSet.TOP, view.id, ConstraintSet.BOTTOM, dpToPx(context, 4).toInt())
                    constraintSet.applyTo(this)
                }
            }
            else -> {

            }
        }

        constraintNowWidth += getTextViewWidth(textView) + dpToPx(context, 4).toInt()
        constraintTextView = textView
    }

    private fun getWidthStatus(textView: TextView): Int {
        return if (constraintTextView == null) STATUS_ATTACH_FIRST
        else if (constraintLayoutWidth > constraintNowWidth + getTextViewWidth(textView)) STATUS_ATTACH_SIDE
        else STATUS_ATTACH_BOTTOM
    }

    private fun getTextViewWidth(textView: TextView): Int{
        textView.measure(0,0)
        return textView.measuredWidth
    }

    private fun getTextViewHeight(textView: TextView): Int{
        textView.measure(0,0)
        return textView.measuredWidth
    }
}