package com.example.wgpgkt.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val retrofitInterface: ApiInterface
    val baseUrl: String = "http://3.39.25.249/api/v1/"

    init {
        var retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitInterface = retrofit.create(ApiInterface::class.java)
    }

    fun getRetrofitInterface(): ApiInterface = retrofitInterface
}