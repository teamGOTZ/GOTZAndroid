package com.example.wgpgkt.views.main.find

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.wgpgkt.R
import com.example.wgpgkt.base.BaseActivity
import com.example.wgpgkt.databinding.ActivityMainFindBinding

class MainFindActivity:BaseActivity<ActivityMainFindBinding>() {
    override val layoutResID: Int = R.layout.activity_main_find
    lateinit var fragmentTransaction:FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //fragment + viewmodel?
        initFragment()
        btnClick()
    }

    fun initFragment(){
        val fragment = MainFindIdFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_main_find, fragment)
        fragmentTransaction.commit()

    }

    override fun btnClick(){
        binding.btnMainFindId.setOnClickListener {
            val fragment = MainFindIdFragment()
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fl_main_find, fragment)
            fragmentTransaction.commit()
        }

        binding.btnMainFindPw.setOnClickListener {
            val fragment = MainFindPwFragment()
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fl_main_find, fragment)
            fragmentTransaction.commit()
        }


    }
}