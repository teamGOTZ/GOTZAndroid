package com.gotz.presentation.view.onboarding

import com.gotz.base.BaseFragment
import com.gotz.presentation.R
import com.gotz.presentation.databinding.FragmentOnboardingTermsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingTermsFragment: BaseFragment<FragmentOnboardingTermsBinding>(R.layout.fragment_onboarding_terms) {

    private val viewModel: OnboardingViewModel by sharedViewModel()

    override fun initFragment() {
        binding.viewmodel = viewModel
    }
}