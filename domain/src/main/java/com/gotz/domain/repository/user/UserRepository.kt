package com.gotz.domain.repository.user

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun createUserName(name: String): Flow<Boolean>

    fun readUserName(): Flow<String>

    suspend fun updateAge(age: Int)

    fun readAge(): Flow<Int>

    suspend fun updateGender(gender: String)

    fun readGender(): Flow<String>
}