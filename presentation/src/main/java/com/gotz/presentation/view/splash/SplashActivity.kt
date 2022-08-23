package com.gotz.presentation.view.splash

import android.content.Intent
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.gotz.domain.usecase.user.ReadSingleNameUseCase
import com.gotz.presentation.R
import com.gotz.presentation.base.BaseActivity
import com.gotz.presentation.databinding.ActivitySplashBinding
import com.gotz.presentation.view.main.MainActivity
import com.gotz.presentation.view.onboarding.OnboardingActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity: BaseActivity<ActivitySplashBinding>(R.layout.activity_splash){
    val readSingleNameUseCase: ReadSingleNameUseCase by inject()

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

            readName()
        }
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }

    fun readName(){
        readSingleNameUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { name ->
                    if(name.isNullOrEmpty()){
                        val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                },
                onError = {

                }
            )
            .addTo(compositeDisposable)
    }
}