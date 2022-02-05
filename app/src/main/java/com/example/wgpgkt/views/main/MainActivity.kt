package com.example.wgpgkt.views.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wgpgkt.R
import com.example.wgpgkt.base.BaseActivity
import com.example.wgpgkt.databinding.ActivityMainBinding
import com.example.wgpgkt.views.main.find.MainFindActivity
import com.example.wgpgkt.views.main.join.MainJoinActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutResID: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnClick()
    }

    override fun btnClick(){
        binding.btnMainLogin.setOnClickListener {
            //로그인 API 호출
            makeToast("로그인")
        }

        binding.btnMainJoin.setOnClickListener {
            val intent = Intent(this, MainJoinActivity::class.java)
            startActivity(intent)
        }

        binding.tvMainFind.setOnClickListener {
            try{
                val intent = Intent(this, MainFindActivity::class.java)
                startActivity(intent)
            }catch (e:Exception){
                Log.e("MainActivity : ", e.message.toString() )
            }
        }
    }
}