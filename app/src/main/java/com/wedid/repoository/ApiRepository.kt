package com.wedid.repoository

import com.wedid.model.LoginModel
import com.wedid.model.RegisterModel
import retrofit2.Response

interface ApiRepository {

    /**** 로그인 관련 API ****/
    // 유저 등록
    suspend fun postRegister(dateOfBirth: String, email: String, password: String, phoneNumber: String, username: String): Response<RegisterModel>

    // 일반 로그인
    suspend fun postLogin(email: String, password: String): Response<LoginModel>

    // 토큰 재발급
    //suspend fun postRefresh()

    // 이메일 찾기

    // 비밀번호 찾기

    /**** 메모 관련 API ****/
    // 메모 리스트 조회

    // 메모 등록

    // 메모 리스트 삭제

    // 메모 단건 조회

    // 메모 수정

    // 메모 단건 삭제

    /**** 일반사용자 관련 API ****/
    // 유저 조회

    // 유저 프로필 조회

    // 유저 프로필 업데이트

    /**** 일정 관련 API ****/
    // 일정 모두 조회

    // 일정 등록

    // 일정 단건 조회

    // 일정 수정

    // 일정 삭제

    // 일정 월별 리스트 조회

    /**** 현직자 관련 API ****/
    // 현직자 이메일 인증


    //fun testAPI(dateOfBirth: String, email: String, password: String, phoneNumber: String, username: String): Call<RegisterModel>

    /*
    suspend fun getAPI(): RetrofitItem
     */
}