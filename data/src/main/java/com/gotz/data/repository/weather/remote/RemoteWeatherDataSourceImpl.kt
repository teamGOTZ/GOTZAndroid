package com.gotz.data.repository.weather.remote

import com.gotz.data.api.WeatherAPI
import com.gotz.data.model.WeatherEntity
import retrofit2.Response

class RemoteWeatherDataSourceImpl(
    private val api: WeatherAPI
) : RemoteWeatherDataSource {

    override suspend fun getWeather(base_date: String, base_time: String, nx: Int, ny: Int): Response<WeatherEntity> {
        return api.getWeather(
            base_date = base_date,
            base_time = base_time,
            nx = nx,
            ny = ny)
    }
}