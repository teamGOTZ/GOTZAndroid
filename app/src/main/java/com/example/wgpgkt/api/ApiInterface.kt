package com.example.wgpgkt.api

import com.example.wgpgkt.model.RegisterBody
import com.example.wgpgkt.model.RegisterModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("v1/register")
    suspend fun postRegister(@Body registerBody: RegisterBody): Response<RegisterModel>

    @POST("v1/register")
    fun test(@Body registerBody: RegisterBody) : Call<RegisterModel>

    /*
    @GET("getUltraSrtNcst")
    suspend fun getData(@Query("serviceKey") serviceKey:String,
                @Query("numOfRows") numOfRows:Int,
                @Query("dataType") dataType:String,
                @Query("base_date") base_date:String,
                @Query("base_time") base_time:String,
                @Query("nx") nx:Int,
                @Query("ny") ny:Int
    ): Response<RetrofitItem>
     */
}