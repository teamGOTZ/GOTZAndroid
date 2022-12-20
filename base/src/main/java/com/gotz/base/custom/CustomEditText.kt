package com.gotz.base.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import com.gotz.base.R

class CustomEditText  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), TextWatcher {
    private var imageView: ImageView
    var editText: EditText
    var text = MutableLiveData<String>()

    init {
        val view = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_edit_text, this, false)
        addView(view)

        imageView = findViewById<ImageView>(R.id.imageView)
        editText = findViewById<EditText>(R.id.editText)

        clearText()
        showHideClearButton()
    }

    private fun clearText(){
        imageView.setOnClickListener {
            editText.setText("")
        }
    }

    private fun showHideClearButton(){
        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        text.value = p0.toString()
        if(p0!!.isNotEmpty()) imageView.visibility = View.VISIBLE
        else imageView.visibility = View.GONE
    }

    override fun afterTextChanged(p0: Editable?) {

    }

}