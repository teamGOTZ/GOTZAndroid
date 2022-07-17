package com.gotz.views.main.prepare

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.InputFilter
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.gotz.databinding.ActivityMainPrepareNameBinding

class MainPrepareNameActivity: AppCompatActivity() {
    private lateinit var _binding: ActivityMainPrepareNameBinding
    private val binding get() = _binding

    //private lateinit var str: String
    private val RESULT_FINISH = 3

    private val viewModel: MainPrepareNameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainPrepareNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //supportActionBar?.hide()

        binding.viewmodel = viewModel

        binding.etMainPrepareName.custom_edittext_et_black100.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20))

        binding.etMainPrepareName.text.observe(this, Observer {
            binding.btnMainPrepareNameNext.isEnabled = it.isNotEmpty()
            viewModel.name.value = it
        })

        binding.btnMainPrepareNameBack.setOnClickListener {
            finish()
            //setResult(0)
        }

        binding.btnMainPrepareNameNext.setOnClickListener {
            val pref = getSharedPreferences("PREF", 0)
            val edit = pref.edit()
            edit.putString("NAME", viewModel.name.value.toString())
            edit.apply()

            //str = pref.getString("NAME","")!!

            //val intent = Intent(this, MainPrepareHelloActivity::class.java)
            //startActivity(intent)
            openActivityForResult(MainPrepareHelloActivity())
        }
    }
    /*
    override fun onRestart() {
        super.onRestart()
    }*/

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val focusView = currentFocus
        if (focusView != null && ev != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()

            if (!rect.contains(x, y)) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun openActivityForResult(activity: Activity){
        val intent = Intent(this, activity::class.java)
        startForResult.launch(intent)
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_FINISH){
            setResult(RESULT_FINISH)
            finish()
        }
        else{
            val pref = getSharedPreferences("PREF", 0)
            val edit = pref.edit()
            edit.putString("NAME", "")
            edit.apply()
        }
    }
}