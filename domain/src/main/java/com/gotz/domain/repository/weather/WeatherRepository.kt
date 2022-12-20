package com.gotz.domain.repository.weather

import com.gotz.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(base_date: String, base_time: String, nx: Int, ny: Int): Weather
}