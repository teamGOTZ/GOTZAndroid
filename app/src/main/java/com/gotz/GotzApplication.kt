package com.gotz

import android.app.Application
import com.gotz.di.dataLayerModule
import com.gotz.di.domainLayerModule
import com.gotz.di.presentationLayerModule
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GotzApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initKakao()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@GotzApplication.applicationContext)
            modules(
                listOf(
                    dataLayerModule,
                    domainLayerModule,
                    presentationLayerModule
                )
            )
        }
    }

    private fun initKakao() {
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }
}