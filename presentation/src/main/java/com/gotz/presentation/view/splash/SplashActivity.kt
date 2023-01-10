package com.gotz.presentation.view.splash

import android.content.Intent
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gotz.domain.usecase.user.ReadNameUseCase
import com.gotz.presentation.R
import com.gotz.base.BaseActivity
import com.gotz.presentation.databinding.ActivitySplashBinding
import com.gotz.base.util.GLog
import com.gotz.presentation.view.main.MainActivity
import com.gotz.presentation.view.tutorial.TutorialActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity: BaseActivity<ActivitySplashBinding>(R.layout.activity_splash){
    val readNameUseCase: ReadNameUseCase by inject()

    override fun initActivity(){
        startActivityWithDelay(3000L)
    }

    private fun startActivityWithDelay(millis: Long){
        lifecycleScope.launch(Dispatchers.Main){
            delay(millis)
            startActivityWithReadName()
        }
    }

    private fun startActivityWithReadName(){
        lifecycleScope.launch(Dispatchers.IO) {
            readNameUseCase().catch { flowCollector ->
                GLog.messageLog(flowCollector.message.toString())
            }.collect{ name ->
                if(name.isEmpty()){
                    val intent = Intent(this@SplashActivity, TutorialActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun initView() {
        Glide.with(this).load(R.raw.gif_splash).into(binding.ivContents)
    }
}