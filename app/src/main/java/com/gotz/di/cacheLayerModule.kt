package com.gotz.di

import android.content.Context
import androidx.datastore.rxjava2.rxDataStore
import com.gotz.cache.datastore.UserDataStoreSerializer
import com.gotz.cache.repository.user.CacheUserDataSourceImpl
import com.gotz.data.source.user.CacheUserDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val Context.rxUserDataStore by rxDataStore(fileName = "user.proto", serializer = UserDataStoreSerializer)

@ExperimentalCoroutinesApi
val cacheLayerModule = module{

    factory<CacheUserDataSource> { CacheUserDataSourceImpl(androidContext().rxUserDataStore) }
}