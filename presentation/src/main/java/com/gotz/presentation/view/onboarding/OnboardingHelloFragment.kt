package com.gotz.presentation.view.onboarding

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.gotz.presentation.R
import com.gotz.presentation.base.BaseFragment
import com.gotz.presentation.databinding.FragmentOnboardingHelloBinding
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingHelloFragment: BaseFragment<FragmentOnboardingHelloBinding>(R.layout.fragment_onboarding_hello) {

    private val viewModel: OnboardingViewModel by sharedViewModel()

    override fun onCreateView() {

    }

    override fun onViewCreated() {
        binding.viewmodel = viewModel
    }

}