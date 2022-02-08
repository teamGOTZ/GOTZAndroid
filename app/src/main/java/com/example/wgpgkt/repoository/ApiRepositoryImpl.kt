package com.example.wgpgkt.repoository

import android.util.Log
import com.example.wgpgkt.api.ApiClient
import com.example.wgpgkt.model.RegisterBody
import com.example.wgpgkt.model.RegisterModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

object ApiRepositoryImpl:ApiRepository {

    override suspend fun postRegister(dateOfBirth: String, email: String, password: String, phoneNumber: String, username: String): RegisterModel {
        val body: RegisterBody = RegisterBody(dateOfBirth, email, password, phoneNumber, username)
        val data = ApiClient.getApiInterface().postRegister(body)
        Log.e("Repository", data.body()?.code.toString() + "\n" + data.body()?.message.toString())
        return data.body()!!
    }

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
    }
}