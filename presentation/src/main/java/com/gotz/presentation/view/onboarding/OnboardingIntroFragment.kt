package com.gotz.presentation.view.onboarding

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.gotz.presentation.R
import com.gotz.presentation.base.BaseFragment
import com.gotz.presentation.databinding.FragmentOnboardingIntroBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingIntroFragment: BaseFragment<FragmentOnboardingIntroBinding>(R.layout.fragment_onboarding_intro) {
    private val onboardingViewModel: OnboardingViewModel by sharedViewModel()
    override fun onCreateView() {

    }

    override fun onViewCreated() {
        binding.viewmodel = onboardingViewModel
    }

}