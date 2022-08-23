package com.gotz.presentation.view.onboarding

import com.gotz.presentation.R
import com.gotz.presentation.base.BaseFragment
import com.gotz.presentation.databinding.FragmentOnboardingNameBinding
import com.gotz.presentation.util.GotzTest.logE
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingNameFragment: BaseFragment<FragmentOnboardingNameBinding>(R.layout.fragment_onboarding_name) {

    private val viewModel: OnboardingViewModel by sharedViewModel()

    override fun onCreateView() {

    }

    override fun onViewCreated() {
        binding.viewmodel = viewModel
        logE(viewModel.nameText.value.toString())
    }

}