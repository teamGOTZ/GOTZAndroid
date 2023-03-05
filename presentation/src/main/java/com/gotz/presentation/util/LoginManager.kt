package com.gotz.presentation.util

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginManager(val context: Context) {

    // 이메일 로그인 콜백
    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            GLog.messageLog("로그인 실패 $error")
        } else if (token != null) {
            GLog.messageLog("로그인 성공 ${token.accessToken}")
            UserApiClient.instance.me { user, error ->
                if(error != null) {
                    GLog.messageLog("사용자 정보 요청 실패 $error")
                }
                else if (user != null) {
                    GLog.messageLog("사용자 정보 요청 성공 $user")
                    GLog.messageLog("nickname::${user.kakaoAccount?.profile?.nickname}")
                    GLog.messageLog("email::${user.kakaoAccount?.email}")
                }
            }
        }
    }

    fun kakaoLogin() {
        // 카카오톡 설치 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                // 로그인 실패 부분
                if (error != null) {
                    GLog.messageLog("로그인 실패 $error")
                    // 사용자가 취소
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = mCallback) // 카카오 이메일 로그인
                    }
                }
                // 로그인 성공 부분
                else if (token != null) {
                    GLog.messageLog("로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = mCallback) // 카카오 이메일 로그인
        }
    }
}