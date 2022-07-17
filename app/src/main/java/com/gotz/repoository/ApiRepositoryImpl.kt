package com.gotz.repoository

import com.gotz.api.ApiClient
import com.gotz.model.*
import retrofit2.Response

object ApiRepositoryImpl:ApiRepository {
    // 유저 등록
    override suspend fun postRegister(dateOfBirth: String, email: String, password: String, phoneNumber: String, username: String): Response<RegisterModel> {
        val body: RegisterBody = RegisterBody(dateOfBirth, email, password, phoneNumber, username)
        val data = ApiClient.getApiInterface().postRegister(body)
        return data
    }
    // 일반 로그인
    override suspend fun postLogin(email: String, password: String): Response<LoginModel> {
        val body: LoginBody = LoginBody(email, password)
        val data = ApiClient.getApiInterface().postLogin(body)
        return data
    }
    // 토큰 재발급

    // 이메일 찾기

    // 비밀번호 찾기

    /*
    override fun testAPI(
        dateOfBirth: String,
        email: String,
        password: String,
        phoneNumber: String,
        username: String,
    ): Call<RegisterModel> {
        val body: RegisterBody = RegisterBody(dateOfBirth, email, password, phoneNumber, username)
        Log.e("Body.dateOfBirth ::", dateOfBirth)
        Log.e("Body.email ::", email)
        Log.e("Body.password :: ", email)
        Log.e("Body.phoneNumber ::", email)
        Log.e("Body.username ::", email)
        return ApiClient.getApiInterface().test(body)
    }*/
}