package com.gotz.api

import com.gotz.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @POST("v1/register")
    suspend fun postRegister(@Body registerBody: RegisterBody): Response<RegisterModel>

    @POST("login")
    @Headers("X-Requested-With: BasicHttpRequest")
    suspend fun postLogin(@Body loginBody: LoginBody): Response<LoginModel>

    //suspend fun postFindEmail(@Body findEmailBody: FindEmailBody): Response<FindEmailModel>

    //suspend fun postFindPassword(@Body findPasswordBody: FindPasswordBody): Response<FindPasswordModel>
    /*
    @POST("v1/register")
    fun test(@Body registerBody: RegisterBody) : Call<RegisterModel>
    */

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