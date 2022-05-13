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

class CustomEditTextBlack100  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), TextWatcher {
    private var custom_edittext_iv_black100: ImageView
    var custom_edittext_et_black100: EditText
    var text = MutableLiveData<String>()

    init {
        val view = LayoutInflater.from(getContext()).inflate(R.layout.custom_edittext_black100, this, false)
        addView(view)

        custom_edittext_iv_black100 = findViewById<ImageView>(R.id.custom_edittext_iv_black100)
        custom_edittext_et_black100 = findViewById<EditText>(R.id.custom_edittext_et_black100)

        clearText()
        showHideClearButton()
    }

    private fun clearText(){
        custom_edittext_iv_black100.setOnClickListener {
            custom_edittext_et_black100.setText("")
        }
    }

    private fun showHideClearButton(){
        custom_edittext_et_black100.addTextChangedListener(this)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        text.value = p0.toString()
        if(p0!!.length > 0) custom_edittext_iv_black100.visibility = View.VISIBLE
        else custom_edittext_iv_black100.visibility = View.INVISIBLE
    }

    override fun afterTextChanged(p0: Editable?) {

    }

}