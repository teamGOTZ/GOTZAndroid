package com.gotz.presentation.view.onboarding

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.gotz.presentation.R
import com.gotz.presentation.base.BaseActivity
import com.gotz.presentation.databinding.ActivityOnboardingBinding
import com.gotz.presentation.util.EventObserver
import com.gotz.presentation.view.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingActivity: BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {

    private val viewModel: OnboardingViewModel by viewModel()

    private var host: Fragment? = null
    private var navController: NavController? = null

    override fun initActivity() {
        binding.viewmodel = viewModel
    }

    override fun initNavigation() {
        host = supportFragmentManager.findFragmentById(R.id.fcv_onboarding)
        navController = host?.findNavController()
    }

    override fun initObserver() {
        viewModel.run{
            btnClickEvent.observe(this@OnboardingActivity, EventObserver{
                navController?.currentDestination?.label.let{ charSequence ->
                    when(charSequence.toString()){
                        "OnboardingTermsFragment" -> {
                            navController?.navigate(R.id.action_fragment_onboarding_terms_to_fragment_onboarding_name)
                        }
                        "OnboardingNameFragment" -> {
                            navController?.navigate(R.id.action_fragment_onboarding_name_to_fragment_onboarding_age)
                        }
                        "OnboardingAgeFragment" -> {
                            navController?.navigate(R.id.action_fragment_onboarding_age_to_fragment_onboarding_gender)
                        }
                        "OnboardingGenderFragment" -> {
                            navController?.navigate(R.id.action_fragment_onboarding_gender_to_fragment_onboarding_hello)
                        }
                        "OnboardingHelloFragment" -> {
                            val intent = Intent(this@OnboardingActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            })

            btnSkipEvent.observe(this@OnboardingActivity, EventObserver{
                navController?.currentDestination?.label.let{ charSequence ->
                    when(charSequence.toString()) {
                        "OnboardingAgeFragment" -> {
                            navController?.navigate(R.id.action_fragment_onboarding_age_to_fragment_onboarding_hello)
                        }
                        "OnboardingGenderFragment" -> {
                            navController?.navigate(R.id.action_fragment_onboarding_gender_to_fragment_onboarding_hello)
                        }
                    }
                }
            })
        }
    }

}