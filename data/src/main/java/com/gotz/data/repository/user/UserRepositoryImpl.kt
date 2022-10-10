package com.gotz.data.repository.user

import com.gotz.data.source.user.CacheUserDataSource
import com.gotz.domain.repository.user.UserRepository
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val cacheUserDataSource: CacheUserDataSource
): UserRepository {
    override suspend fun createUserName(name: String): Flow<Boolean> =
        cacheUserDataSource.createUserName(name)

    override fun readUserName(): Flow<String> =
        cacheUserDataSource.readUserName()
}