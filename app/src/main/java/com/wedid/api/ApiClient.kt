package com.wedid.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val apiInterface: ApiInterface
    val retrofit:Retrofit
    val baseUrl: String = "http://3.39.25.249/api/"
    var clientBuilder = OkHttpClient.Builder()
    var loggingInterceptor = HttpLoggingInterceptor()


    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    fun getApiInterface(): ApiInterface = apiInterface
}