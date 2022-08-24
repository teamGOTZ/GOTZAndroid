package com.gotz.di

import com.gotz.data.repository.user.UserRepositoryImpl
import com.gotz.domain.repository.user.UserRepository
import org.koin.dsl.module

val dataLayerModule = module {
    factory<UserRepository> { UserRepositoryImpl(get()) }
}