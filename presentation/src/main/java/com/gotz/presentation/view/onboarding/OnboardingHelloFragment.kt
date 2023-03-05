package com.gotz.presentation.view.onboarding

import com.bumptech.glide.Glide
import com.gotz.presentation.R
import com.gotz.presentation.base.BaseFragment
import com.gotz.presentation.databinding.FragmentOnboardingHelloBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingHelloFragment: BaseFragment<FragmentOnboardingHelloBinding>(R.layout.fragment_onboarding_hello) {

    private val viewModel: OnboardingViewModel by sharedViewModel()

    override fun initFragment() {
        binding.viewmodel = viewModel
        Glide.with(this).load(R.raw.gif_on_boarding_6).into(binding.ivCharacter)
    }
}