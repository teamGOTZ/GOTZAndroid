package com.gotz.presentation.view.splash

import android.content.Intent
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.lifecycle.lifecycleScope
import com.gotz.domain.usecase.user.ReadSingleNameUseCase
import com.gotz.presentation.R
import com.gotz.base.BaseActivity
import com.gotz.presentation.databinding.ActivitySplashBinding
import com.gotz.presentation.view.main.MainActivity
import com.gotz.presentation.view.onboarding.OnboardingActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity: BaseActivity<ActivitySplashBinding>(R.layout.activity_splash){
    val readSingleNameUseCase: ReadSingleNameUseCase by inject()

    override fun initActivity(){

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

            readName()
        }
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }

    private fun readName(){
        lifecycleScope.launch(Dispatchers.IO) {
            readSingleNameUseCase().collect { name ->
                if(name.isNullOrEmpty()){
                    val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}