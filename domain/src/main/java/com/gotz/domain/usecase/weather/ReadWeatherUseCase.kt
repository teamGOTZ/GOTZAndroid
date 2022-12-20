package com.gotz.domain.usecase.weather

import com.gotz.domain.model.Weather
import com.gotz.domain.repository.weather.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal typealias ReadWeatherBaseUseCase = (String, String, Int, Int) -> Flow<Weather>

class ReadWeatherUseCase(
    private val weatherRepository: WeatherRepository
): ReadWeatherBaseUseCase {
    override fun invoke(base_date: String, base_time: String, nx: Int, ny: Int): Flow<Weather> =
        flow {
            emit(weatherRepository.getWeather(base_date, base_time, nx, ny))
        }

}