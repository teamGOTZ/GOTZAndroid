package com.gotz.data.source.user

import io.reactivex.Single

interface CacheUserDataSource {

    fun createUserName(name: String): Single<Boolean>
    fun readUserName(): Single<String>
}