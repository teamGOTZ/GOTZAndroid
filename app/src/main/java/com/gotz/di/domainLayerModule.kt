package com.gotz.di

import com.gotz.domain.usecase.user.CreateNameUseCase
import com.gotz.domain.usecase.user.ReadSingleNameUseCase
import com.gotz.presentation.view.onboarding.OnboardingViewModel
import com.gotz.presentation.view.tutorial.TutorialViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val domainLayerModule = module {

    /**
     * User UseCase
     */
    factory{ ReadSingleNameUseCase(get())}
    factory{ CreateNameUseCase(get()) }

    /**
     * ViewModel
     */

    viewModel { OnboardingViewModel(get()) }
    viewModel { TutorialViewModel() }
}