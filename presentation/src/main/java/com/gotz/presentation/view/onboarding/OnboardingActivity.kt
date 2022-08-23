package com.gotz.presentation.view.onboarding

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.gotz.presentation.R
import com.gotz.presentation.base.BaseActivity
import com.gotz.presentation.databinding.ActivityOnboardingBinding
import com.gotz.presentation.util.Utils
import com.gotz.presentation.util.EventObserver
import com.gotz.presentation.util.GotzTest.logE
import com.gotz.presentation.view.main.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingActivity: BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {

    private val viewModel: OnboardingViewModel by viewModel()

    private var host: Fragment? = null
    private var navController: NavController? = null

    override fun onCreate() {
        binding.viewmodel = viewModel
    }

    override fun initNavigation() {
        host = supportFragmentManager.findFragmentById(R.id.fcv_onboarding)
        navController = host?.findNavController()
    }

    override fun initObserver() {
        viewModel.btnClickEvent.observe(this, EventObserver{
            navController?.currentDestination?.label.let{ charSequence ->
                val fragment = charSequence.toString()
                logE(fragment)
                when(fragment){
                    "OnboardingIntroFragment" -> {
                        navController?.navigate(R.id.action_fragment_onboarding_intro_to_fragment_onboarding_name)
                    }
                    "OnboardingNameFragment" -> {
                        navController?.navigate(R.id.action_fragment_onboarding_name_to_fragment_onboarding_hello)
                    }
                    "OnboardingHelloFragment" -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        })
    }

}