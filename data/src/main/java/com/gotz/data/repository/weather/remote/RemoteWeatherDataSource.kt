package com.gotz.data.repository.weather.remote

import com.gotz.data.model.WeatherEntity
import retrofit2.Response

interface RemoteWeatherDataSource {
    suspend fun getWeather(base_date: String, base_time: String, nx: Int, ny: Int): Response<WeatherEntity>
}