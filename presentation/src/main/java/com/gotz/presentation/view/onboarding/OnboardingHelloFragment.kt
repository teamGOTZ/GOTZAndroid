package com.gotz.presentation.view.onboarding

import com.gotz.presentation.R
import com.gotz.base.BaseFragment
import com.gotz.presentation.databinding.FragmentOnboardingHelloBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingHelloFragment: BaseFragment<FragmentOnboardingHelloBinding>(R.layout.fragment_onboarding_hello) {

    private val viewModel: OnboardingViewModel by sharedViewModel()

    override fun initFragment() {
        binding.viewmodel = viewModel
    }
}