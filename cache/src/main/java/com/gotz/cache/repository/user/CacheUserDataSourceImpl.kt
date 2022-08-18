package com.gotz.cache.repository.user

import androidx.datastore.rxjava2.RxDataStore
import com.gotz.cache.User.UserDataStore
import com.gotz.data.source.user.CacheUserDataSource
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CacheUserDataSourceImpl(
    private val userDataStore: RxDataStore<UserDataStore>
): CacheUserDataSource {
    override fun insertUserName(name: String): Single<Boolean> =
        userDataStore.updateDataAsync{ dataStore ->
            Single.just(dataStore.toBuilder().setName(name).build())
        }.map{ dataStore ->
            dataStore.name.isNullOrEmpty().not()
        }


    override fun readUserName(): Single<String> =
        userDataStore.updateDataAsync{ dataStore ->
            Single.just(dataStore.toBuilder().build())
        }.map { dataStore ->
            dataStore.name
        }

}