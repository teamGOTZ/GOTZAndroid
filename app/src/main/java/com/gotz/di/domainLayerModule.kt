package com.gotz.di

import com.gotz.domain.usecase.schedule.*
import com.gotz.domain.usecase.user.*
import com.gotz.domain.usecase.weather.ReadWeatherUseCase
import org.koin.dsl.module

val domainLayerModule = module {

    /**
     * User UseCase
     */
    factory { ReadNameUseCase(get())}
    factory { CreateNameUseCase(get()) }
    factory { ReadAgeUseCase(get()) }
    factory { UpdateAgeUseCase(get()) }
    factory { ReadGenderUseCase(get()) }
    factory { UpdateGenderUseCase(get()) }

    /**
     * Schedule UseCase
     */
    factory { CreateScheduleUseCase(get()) }
    factory { ReadAllScheduleUseCase(get()) }
    factory { ReadDailyScheduleUseCase(get()) }
    factory { ReadMonthlyScheduleUseCase(get()) }
    factory { UpdateScheduleUseCase(get()) }
    factory { DeleteScheduleUseCase(get()) }

    /**
     * Weather UseCase
     */
    factory { ReadWeatherUseCase(get()) }

}