package com.wedid.views.main.prepare

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.wedid.databinding.ActivityMainPrepareSplashBinding
import com.wedid.views.frame.FrameActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainPrepareSplashActivity: AppCompatActivity() {
    private var _binding: ActivityMainPrepareSplashBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainPrepareSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //supportActionBar?.hide()
        lateinit var intent: Intent
        val pref = getSharedPreferences("PREF",0)
        val name = pref.getString("NAME", "")

        setAnimaition()
        GlobalScope.launch {
            setAnimaition()
            delay(3000)
            // 권한 허용 필요
            // Shared Data 확인 후 Intro or Home
            if(name!!.length == 0) intent = Intent(applicationContext, MainPrepareIntroActivity::class.java)
            else intent = Intent(applicationContext, FrameActivity::class.java)
            startActivity(intent)

        }
    }
    fun setAnimaition(){
        val fadeIn = AlphaAnimation(0f,1f)
        fadeIn.interpolator = FastOutLinearInInterpolator()
        fadeIn.duration = 3000

        val animation = AnimationSet(false)
        animation.addAnimation(fadeIn)
        binding.ivMainPrepareSplash.animation = animation
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }
}