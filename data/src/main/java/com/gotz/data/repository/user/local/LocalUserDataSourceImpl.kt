package com.gotz.data.repository.user.local

import androidx.datastore.core.DataStore
import com.gotz.data.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class LocalUserDataSourceImpl(
    private val userDataStore: DataStore<User.UserDataStore>
): LocalUserDataSource {
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

    override suspend fun updateUserAge(age: Int) {
        userDataStore.updateData { pref ->
            pref.toBuilder().setAge(age).build()
        }
    }

    override fun readUserAge(): Flow<Int> =
        userDataStore.data.map {
            it.age
        }

    override suspend fun updateUserGender(gender: String) {
        userDataStore.updateData { pref ->
            pref.toBuilder().setGender(gender).build()
        }
    }

    override fun readUserGender(): Flow<String> =
        userDataStore.data.map {
            it.gender
        }
}