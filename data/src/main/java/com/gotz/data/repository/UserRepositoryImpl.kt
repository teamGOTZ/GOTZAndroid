package com.gotz.data.repository

import com.gotz.data.source.user.CacheUserDataSource
import com.gotz.domain.repository.user.UserRepository
import io.reactivex.Single

class UserRepositoryImpl(
    private val cacheUserDataSource: CacheUserDataSource
): UserRepository {
    override fun insertUserName(name: String): Single<Boolean> =
        cacheUserDataSource.insertUserName(name)

    override fun readUserName(): Single<String> =
        cacheUserDataSource.readUserName()
}