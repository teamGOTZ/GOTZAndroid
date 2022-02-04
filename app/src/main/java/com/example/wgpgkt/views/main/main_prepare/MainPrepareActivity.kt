package com.example.wgpgkt.views.main.main_prepare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wgpgkt.R
import com.example.wgpgkt.views.main.main_.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainPrepareActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_prepare)

        val intent =  Intent(this, MainActivity::class.java)

        GlobalScope.launch {
            delay(3000)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }
}