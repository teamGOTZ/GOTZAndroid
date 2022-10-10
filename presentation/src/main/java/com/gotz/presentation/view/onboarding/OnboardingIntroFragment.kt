package com.gotz.presentation.view.onboarding

import com.gotz.presentation.R
import com.gotz.base.BaseFragment
import com.gotz.presentation.databinding.FragmentOnboardingIntroBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingIntroFragment: BaseFragment<FragmentOnboardingIntroBinding>(R.layout.fragment_onboarding_intro) {

    private val viewModel: OnboardingViewModel by sharedViewModel()

    override fun initFragment() {
        binding.viewmodel = viewModel
    }
}