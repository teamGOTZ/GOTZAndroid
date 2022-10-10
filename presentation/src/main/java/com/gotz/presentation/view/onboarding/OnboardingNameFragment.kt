package com.gotz.presentation.view.onboarding

import com.gotz.presentation.R
import com.gotz.base.BaseFragment
import com.gotz.presentation.databinding.FragmentOnboardingNameBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingNameFragment: BaseFragment<FragmentOnboardingNameBinding>(R.layout.fragment_onboarding_name) {

    private val viewModel: OnboardingViewModel by sharedViewModel()

    override fun initFragment() {
        binding.viewmodel = viewModel
    }
}