package com.gotz.data.repository.user

import com.gotz.data.repository.user.local.LocalUserDataSource
import com.gotz.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val cacheUserDataSource: LocalUserDataSource
): UserRepository {
    override suspend fun createUserName(name: String): Flow<Boolean> =
        cacheUserDataSource.createUserName(name)

    override fun readUserName(): Flow<String> =
        cacheUserDataSource.readUserName()

    override suspend fun updateAge(age: Int) {
        cacheUserDataSource.updateUserAge(age)
    }

    override fun readAge(): Flow<Int> =
        cacheUserDataSource.readUserAge()

    override suspend fun updateGender(gender: String) {
        cacheUserDataSource.updateUserGender(gender)
    }

    override fun readGender(): Flow<String> =
        cacheUserDataSource.readUserGender()
}