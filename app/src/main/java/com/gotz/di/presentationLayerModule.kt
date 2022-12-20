package com.gotz.di

import com.gotz.presentation.view.calendar.WeatherViewModel
import com.gotz.presentation.view.calendar.calendar.CalendarViewModel
import com.gotz.presentation.view.calendar.schedule.ScheduleViewModel
import com.gotz.presentation.view.onboarding.OnboardingViewModel
import com.gotz.presentation.view.tutorial.TutorialViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationLayerModule = module{

    /**
     * ViewModel
     */

    viewModel { OnboardingViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { TutorialViewModel() }
    viewModel { ScheduleViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { CalendarViewModel() }
    viewModel { WeatherViewModel(get()) }
}