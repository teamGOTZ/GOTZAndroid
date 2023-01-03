package com.gotz.base.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.gotz.base.R
import com.gotz.base.extension.gone
import com.gotz.base.extension.visible

class CheckLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    companion object{
        const val STATUS_ENABLE = 1
        const val STATUS_DISABLE = 2
    }

    private var layoutStatus: Int = 0

    var layout: ConstraintLayout
    var imageView: ImageView
    var textView: TextView

    init {
        val view = LayoutInflater.from(getContext()).inflate(R.layout.layout_check, this, false)
        addView(view)

        layout = findViewById(R.id.layout)
        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)
    }

    fun setText(text: String) {
        textView.text = text
    }

    private fun setView() {
        if(layoutStatus == STATUS_ENABLE) {
            layout.background = ResourcesCompat.getDrawable(resources, R.drawable.style_check_layout_enable, null)
            imageView.visible()
            textView.setTextColor(resources.getColor(R.color.Primary, null))
        }

        else {
            layout.background = ResourcesCompat.getDrawable(resources, R.drawable.style_check_layout_disable, null)
            imageView.gone()
            textView.setTextColor(resources.getColor(R.color.Gray_900, null))
        }
    }

    fun setLayoutStatus(layoutStatus: Int) {
        this.layoutStatus = layoutStatus
        setView()
    }

    fun getLayoutStatus(): Int =
        layoutStatus
}