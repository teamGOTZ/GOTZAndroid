package com.gotz.domain.repository.user

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun createUserName(name: String): Flow<Boolean>

    fun readUserName(): Flow<String>
}