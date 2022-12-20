package com.gotz.base.custom

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.webkit.*
import android.webkit.WebView
import android.webkit.WebResourceRequest
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebResourceResponse
import android.webkit.WebResourceError

class CustomWebView : WebView {

    interface LoadListener {
        fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
        fun shouldOverrideUrlLoading(view: WebView?, url: String?)
        fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?)
        fun onPageFinished(view: WebView?, url: String?)
    }

    interface ErrorStateListener {
        fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError)
        fun onReceivedHttpError(view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse)
        fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError)
    }

    var loadListener: LoadListener? = null
    var errorStateListener: ErrorStateListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        init()
    }

    private fun init(){
        clearCache(true)
        isVerticalScrollBarEnabled = false

        settings.run {

            javaScriptEnabled = true //자바스크립트 사용여부
            domStorageEnabled = true
            cacheMode = WebSettings.LOAD_DEFAULT // 캐쉬 사용 모드 설정
            useWideViewPort = true //뷰 포트 메타 태그 활성화 여부
            loadWithOverviewMode = true //화면에 맞게 웹뷰 사이즈를 정의
            setSupportMultipleWindows(false) //웹뷰의 다중창 지원 여부
        }

        webChromeClient = CustomWebChromeClient()
        webViewClient = CustomWebViewClient()
    }


    inner class CustomWebViewClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            loadListener?.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            loadListener?.shouldOverrideUrlLoading(view, request)
            return true
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            loadListener?.shouldOverrideUrlLoading(view, url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            loadListener?.onPageFinished(view, url)
        }
    }


    class CustomWebChromeClient : WebChromeClient()


}