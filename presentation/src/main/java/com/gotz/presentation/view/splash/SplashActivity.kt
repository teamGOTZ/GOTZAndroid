package com.gotz.presentation.view.splash

import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.gotz.presentation.R
import com.gotz.presentation.base.BaseActivity
import com.gotz.presentation.databinding.ActivitySplashBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity: BaseActivity<ActivitySplashBinding>(R.layout.activity_splash){

    override fun onCreate(){

        setAnimation()
        startActivityOnDelay()
    }

    private fun setAnimation(){
        val fadeIn = AlphaAnimation(0f, 1f)
        val animation = AnimationSet(false)

        fadeIn.apply {
            interpolator = FastOutLinearInInterpolator()
            duration = 3000
        }

        animation.addAnimation(fadeIn)
        binding.ivSplash.animation = animation
    }

    private fun startActivityOnDelay(){
        GlobalScope.launch{
            delay(3000)

        }
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }
}