package com.gotz.data.api

import com.gotz.data.BuildConfig
import com.gotz.data.model.WeatherEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("getUltraSrtNcst")
    suspend fun getWeather(
        @Query("serviceKey") serviceKey: String = BuildConfig.GOTZ_SERVICE_KEY_WEATHER,
        @Query("dataType") dataType: String = "JSON",
        @Query("base_date") base_date: String,
        @Query("base_time") base_time: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int
    ): Response<WeatherEntity>
}