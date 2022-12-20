package com.gotz.data.repository.weather

import com.gotz.data.mapper.WeatherMapper.toModel
import com.gotz.data.repository.weather.remote.RemoteWeatherDataSource
import com.gotz.domain.model.Weather
import com.gotz.domain.repository.weather.WeatherRepository

class WeatherRepositoryImpl(
    private val remoteWeatherDataSource: RemoteWeatherDataSource
):WeatherRepository {

    override suspend fun getWeather(base_date: String, base_time: String, nx: Int, ny: Int): Weather {
        val result = remoteWeatherDataSource.getWeather(base_date, base_time, nx, ny)
        return result.body()?.toModel() ?: Weather(skyStatus = -1, temperature = 0.0F)
    }
}