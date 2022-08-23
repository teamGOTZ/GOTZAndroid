package com.gotz.presentation.view.onboarding

import com.gotz.presentation.R
import com.gotz.presentation.base.BaseFragment
import com.gotz.presentation.databinding.FragmentOnboardingIntroBinding
import com.gotz.presentation.util.GotzTest.logE
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingIntroFragment: BaseFragment<FragmentOnboardingIntroBinding>(R.layout.fragment_onboarding_intro) {

    private val viewModel: OnboardingViewModel by sharedViewModel()

    override fun onCreateView() {

    }

    override fun onViewCreated() {
        binding.viewmodel = viewModel
        logE(viewModel.nameText.value.toString())
    }

}