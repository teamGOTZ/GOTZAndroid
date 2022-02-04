package com.example.wgpgkt.views.main.main_find

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.example.wgpgkt.R
import com.example.wgpgkt.base.BaseActivity
import com.example.wgpgkt.databinding.ActivityMainFindBinding
import com.example.wgpgkt.views.main.main_join.MainJoinActivity
import com.example.wgpgkt.views.main.main_login.MainLoginActivity

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

    fun btnClick(){
        binding.btnMainFindId.setOnClickListener {
            val fragment = MainFindIdFragment()
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fl_main_find, fragment)
            fragmentTransaction.commit()
        }
// 여기 고쳐야됨
        /*
        binding.btnMainFindPw.setOnClickListener {
            val fragment = MainFindPwFragment()
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fl_main_find, fragment)
            fragmentTransaction.commit()
        }

         */
//
    }
}