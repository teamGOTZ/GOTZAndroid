package com.gotz.presentation.view.onboarding

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.gotz.presentation.R
import com.gotz.presentation.base.BaseActivity
import com.gotz.presentation.databinding.ActivityOnboardingBinding
import com.gotz.presentation.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingActivity: BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    private val onboardingViewModel: OnboardingViewModel by viewModel()
    override fun onCreate() {
        val host = supportFragmentManager.findFragmentById(R.id.fcv_onboarding)
        val navController = host?.findNavController()
        Log.e("OnboardingActivity", host.toString())
        Log.e("OnboardingActivity", navController.toString())
        navController?.let{
            Log.e("OnboardingActivity", it.currentDestination?.label.toString())
        }

        binding.viewmodel = onboardingViewModel

        onboardingViewModel.btnClickEvent.observe(this, EventObserver{
            navController?.currentDestination?.label.let{ charSequence ->
                val fragment = charSequence.toString()
                Log.e("OnboardingActivity", fragment)
                when(fragment){
                    "OnboardingIntroFragment" -> {
                        navController?.navigate(R.id.action_fragment_onboarding_intro_to_fragment_onboarding_name)
                    }
                    "OnboardingNameFragment" -> {
                        navController?.navigate(R.id.action_fragment_onboarding_name_to_fragment_onboarding_hello)
                    }
                    "OnboardingHelloFragment" -> {

                    }
                }
            }
        })
    }
}