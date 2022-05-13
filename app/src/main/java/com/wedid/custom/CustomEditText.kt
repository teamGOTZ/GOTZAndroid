package com.wedid.custom

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
import com.wedid.R

class CustomEditText  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), TextWatcher {
    private var custom_edittext_iv: ImageView
    var custom_edittext_et: EditText
    var text = MutableLiveData<String>()

    init {
        val view = LayoutInflater.from(getContext()).inflate(R.layout.custom_edittext, this, false)
        addView(view)

        custom_edittext_iv = findViewById<ImageView>(R.id.custom_edittext_iv)
        custom_edittext_et = findViewById<EditText>(R.id.custom_edittext_et)

        clearText()
        showHideClearButton()
    }

    private fun clearText(){
        custom_edittext_iv.setOnClickListener {
            custom_edittext_et.setText("")
        }
    }

    private fun showHideClearButton(){
        custom_edittext_et.addTextChangedListener(this)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        text.value = p0.toString()
        if(p0!!.length > 0) custom_edittext_iv.visibility = View.VISIBLE
        else custom_edittext_iv.visibility = View.INVISIBLE
    }

    override fun afterTextChanged(p0: Editable?) {

    }

}