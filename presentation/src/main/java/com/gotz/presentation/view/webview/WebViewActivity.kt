package com.gotz.presentation.view.webview

import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.webkit.*
import com.gotz.presentation.base.BaseActivity
import com.gotz.presentation.custom.CustomWebView
import com.gotz.presentation.R
import com.gotz.presentation.databinding.ActivityWebviewBinding

class WebViewActivity : BaseActivity<ActivityWebviewBinding>(R.layout.activity_webview) {

    companion object {
        const val EXTRA_LOAD_URL: String = "EXTRA_LOAD_URL"
        const val EXTRA_TITLE: String = "EXTRA_TITLE"
    }

    override fun initActivity() {

    }

    override fun initView() {
        binding.run{
            webView.loadListener = object : CustomWebView.LoadListener {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    showProgressBar()
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?) {
                }

                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) {
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    hideProgressBar()
                }
            }

            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun initData() {
        loadUrl(intent)
        setTitle(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        loadUrl(intent)
    }

    private fun loadUrl(intent: Intent?) {
        val url = intent?.getStringExtra(EXTRA_LOAD_URL) ?: ""
        if (url.isNotEmpty()) {
            binding.webView.loadUrl(url)
        }
    }

    private fun setTitle(intent: Intent?) {
        val title = intent?.getStringExtra(EXTRA_TITLE) ?: ""
        binding.textview.text = title
    }

    private fun showProgressBar() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbLoading.visibility = View.GONE
    }
}