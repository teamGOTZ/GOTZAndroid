package com.gotz.di

import com.gotz.domain.usecase.user.CreateNameUseCase
import com.gotz.domain.usecase.user.ReadSingleNameUseCase
import org.koin.dsl.module

val domainLayerModule = module {

    /**
     * User UseCase
     */
    factory{ ReadSingleNameUseCase(get())}
    factory{ CreateNameUseCase(get())}
}