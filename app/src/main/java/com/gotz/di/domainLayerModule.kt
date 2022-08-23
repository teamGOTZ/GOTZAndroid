package com.gotz.di

import com.gotz.domain.usecase.user.InsertNameUseCase
import com.gotz.domain.usecase.user.ReadSingleNameUseCase
import com.gotz.presentation.view.onboarding.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val domainLayerModule = module {

    /**
     * User UseCase
     */
    factory{ ReadSingleNameUseCase(get())}
    factory{ InsertNameUseCase(get()) }

    /**
     * ViewModel
     */

    viewModel { OnboardingViewModel(get()) }
}