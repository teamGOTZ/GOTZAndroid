package com.gotz.cache.repository.user

import androidx.datastore.core.DataStore
import com.gotz.cache.User.UserDataStore
import com.gotz.data.source.user.CacheUserDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class CacheUserDataSourceImpl(
    private val userDataStore: DataStore<UserDataStore>
): CacheUserDataSource {
    override suspend fun createUserName(name: String): Flow<Boolean> =
        flow{
            emit(userDataStore.updateData { dataStore ->
                dataStore.toBuilder().setName(name).build()
            }.name.isNullOrEmpty().not())
        }

    override fun readUserName(): Flow<String> =
        userDataStore.data.map { dataStore ->
            dataStore.name
        }
}