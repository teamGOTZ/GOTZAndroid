package com.example.wgpgkt.views.main.prepare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wgpgkt.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainPrepareSplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_prepare_splash)
        //supportActionBar?.hide()
        val intent =  Intent(this, MainPrepareIntroActivity::class.java)

        GlobalScope.launch {
            delay(3000)
            // 권한 허용 필요
            // Shared Data 확인 후 Intro or Home
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }
}