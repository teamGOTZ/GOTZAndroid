package com.gotz.presentation.view.terms

import android.content.Intent
import com.gotz.base.BaseActivity
import com.gotz.presentation.BuildConfig
import com.gotz.presentation.R
import com.gotz.presentation.databinding.ActivityTermsBinding
import com.gotz.presentation.view.webview.WebViewActivity
import com.gotz.presentation.view.webview.WebViewActivity.Companion.EXTRA_LOAD_URL

class TermsActivity: BaseActivity<ActivityTermsBinding>(R.layout.activity_terms) {

    override fun initActivity() {
        binding.run {
            clTerms.setOnClickListener {
                val intent = Intent(this@TermsActivity, WebViewActivity::class.java).apply {
                    putExtra(EXTRA_LOAD_URL, BuildConfig.GOTZ_URL_TERMS_AND_CONDITIONS)
                }
                startActivity(intent)
            }

            clPrivacy.setOnClickListener {
                val intent = Intent(this@TermsActivity, WebViewActivity::class.java).apply {
                    putExtra(EXTRA_LOAD_URL, BuildConfig.GOTZ_URL_PRIVACY)
                }
                startActivity(intent)
            }
        }
    }
}