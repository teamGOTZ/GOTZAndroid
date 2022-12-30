package com.gotz.presentation.view.onboarding

import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.gotz.presentation.R
import com.gotz.base.BaseFragment
import com.gotz.base.extension.invisible
import com.gotz.base.extension.visible
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
                if(ivCheck.visibility == View.INVISIBLE) {
                    ivCheck.visible()
                    ivTermsCheck.visible()
                    ivPrivacyCheck.visible()
                    btnNext.isEnabled = true
                    termsChecked = true
                    privacyChecked = true
                }
                else {
                    ivCheck.invisible()
                    ivTermsCheck.invisible()
                    ivPrivacyCheck.invisible()
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
                ivCheck.invisible()
                ivTermsCheck.invisible()
                btnNext.isEnabled = false
            }
            else {
                termsChecked = true
                ivTermsCheck.visible()
                if(privacyChecked) {
                    ivCheck.visible()
                    btnNext.isEnabled = true
                }
            }
        }
    }

    private fun setPrivacyCheckStatus() {
        binding.run {
            if(privacyChecked) {
                privacyChecked = false
                ivCheck.invisible()
                ivPrivacyCheck.invisible()
                btnNext.isEnabled = false
            }
            else {
                privacyChecked = true
                ivPrivacyCheck.visible()
                if(termsChecked) {
                    ivCheck.visible()
                    btnNext.isEnabled = true
                }
            }
        }
    }
}