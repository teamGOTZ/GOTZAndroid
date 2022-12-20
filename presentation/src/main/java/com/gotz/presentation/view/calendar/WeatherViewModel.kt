package com.gotz.presentation.view.calendar

import com.gotz.base.BaseViewModel
import com.gotz.domain.model.Weather
import com.gotz.domain.usecase.weather.ReadWeatherUseCase
import kotlinx.coroutines.flow.Flow

class WeatherViewModel(
    private val readWeatherUseCase: ReadWeatherUseCase
): BaseViewModel() {

    fun readWeather(baseDate: String, baseTime: String, nx: Int, ny: Int): Flow<Weather> =
        readWeatherUseCase(baseDate, baseTime, nx, ny)
}