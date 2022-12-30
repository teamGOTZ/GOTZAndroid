package com.gotz.presentation.view.onboarding

import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import com.gotz.presentation.R
import com.gotz.base.BaseFragment
import com.gotz.presentation.databinding.FragmentOnboardingNameBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingNameFragment: BaseFragment<FragmentOnboardingNameBinding>(R.layout.fragment_onboarding_name) {

    private val viewModel: OnboardingViewModel by sharedViewModel()

    override fun initFragment() {
        binding.viewmodel = viewModel

        Glide.with(this).load(R.raw.gif_on_boarding_5).into(binding.ivContents)
    }

    override fun initView() {

        binding.run {
            etOnboardingName.editText.doOnTextChanged { text, start, before, count ->
                text?.let{
                    viewModel.onTextChanged(text, start, before, count)
                }
            }

            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}