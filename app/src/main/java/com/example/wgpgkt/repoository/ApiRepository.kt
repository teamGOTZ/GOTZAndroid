package com.example.wgpgkt.repoository

import com.example.wgpgkt.model.RegisterModel
import retrofit2.Call

interface ApiRepository {

    suspend fun postRegister(dateOfBirth: String, email: String, password: String, phoneNumber: String, username: String): RegisterModel

    fun testAPI(dateOfBirth: String, email: String, password: String, phoneNumber: String, username: String): Call<RegisterModel>
    /*
    suspend fun getAPI(): RetrofitItem
     */
}