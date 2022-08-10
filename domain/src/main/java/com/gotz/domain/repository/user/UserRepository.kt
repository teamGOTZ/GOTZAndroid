package com.gotz.domain.repository.user

import io.reactivex.Single

interface UserRepository {

    fun createUserName(name: String): Single<Boolean>
    fun readUserName(): Single<String>
}