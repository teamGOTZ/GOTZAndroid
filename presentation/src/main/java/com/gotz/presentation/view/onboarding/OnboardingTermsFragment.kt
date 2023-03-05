package com.gotz.presentation.view.onboarding

import android.content.Intent
import com.bumptech.glide.Glide
import com.gotz.presentation.R
import com.gotz.presentation.base.BaseFragment
import com.gotz.presentation.extension.disable
import com.gotz.presentation.extension.enable
import com.gotz.presentation.BuildConfig
import com.gotz.presentation.databinding.FragmentOnboardingTermsBinding
import com.gotz.presentation.view.webview.WebViewActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingTermsFragment: BaseFragment<FragmentOnboardingTermsBinding>(R.layout.fragment_onboarding_terms) {

    private val viewModel: OnboardingViewModel by sharedViewModel()

    private var termsChecked = false
    private var privacyChecked = false

    override fun initFragment() {
        binding.viewmodel = viewModel

        Glide.with(this).load(R.raw.gif_on_boarding_5).into(binding.ivContents)
    }

    override fun initView() {
        binding.run {
            btnBack.setOnClickListener {
                requireActivity().finish()
            }

            llAgreeAll.setOnClickListener {
                if(!termsChecked || !privacyChecked) {
                    ivCheck.enable()
                    ivTermsCheck.enable()
                    ivPrivacyCheck.enable()
                    btnNext.isEnabled = true
                    termsChecked = true
                    privacyChecked = true
                }
                else {
                    ivCheck.disable()
                    ivTermsCheck.disable()
                    ivPrivacyCheck.disable()
                    btnNext.isEnabled = false
                    termsChecked = false
                    privacyChecked = false
                }
            }

            ivTermsCheck.setOnClickListener {
                setTermsCheckStatus()
            }

            tvTerms.setOnClickListener {
                setTermsCheckStatus()
            }

            ivPrivacyCheck.setOnClickListener {
                setPrivacyCheckStatus()
            }

            tvPrivacy.setOnClickListener {
                setPrivacyCheckStatus()
            }

            tvTermsMore.setOnClickListener {
                context?.let {
                    val intent = Intent(it, WebViewActivity::class.java).apply {
                        putExtra(WebViewActivity.EXTRA_LOAD_URL, BuildConfig.GOTZ_URL_TERMS_AND_CONDITIONS)
                        putExtra(WebViewActivity.EXTRA_TITLE, "서비스 이용 약관")
                    }
                    startActivity(intent)
                }
            }

            tvPrivacyMore.setOnClickListener {
                context?.let {
                    val intent = Intent(it, WebViewActivity::class.java).apply {
                        putExtra(WebViewActivity.EXTRA_LOAD_URL, BuildConfig.GOTZ_URL_PRIVACY)
                        putExtra(WebViewActivity.EXTRA_TITLE, "개인 정보 보호 방침")
                    }
                    startActivity(intent)
                }
            }
        }
    }

    private fun setTermsCheckStatus() {
        binding.run {
            if(termsChecked) {
                termsChecked = false
                ivCheck.disable()
                ivTermsCheck.disable()
                btnNext.isEnabled = false
            }
            else {
                termsChecked = true
                ivTermsCheck.enable()
                if(privacyChecked) {
                    ivCheck.enable()
                    btnNext.isEnabled = true
                }
            }
        }
    }

    private fun setPrivacyCheckStatus() {
        binding.run {
            if(privacyChecked) {
                privacyChecked = false
                ivCheck.disable()
                ivPrivacyCheck.disable()
                btnNext.isEnabled = false
            }
            else {
                privacyChecked = true
                ivPrivacyCheck.enable()
                if(termsChecked) {
                    ivCheck.enable()
                    btnNext.isEnabled = true
                }
            }
        }
    }
}