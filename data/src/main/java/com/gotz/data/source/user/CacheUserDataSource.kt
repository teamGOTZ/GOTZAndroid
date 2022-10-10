package com.gotz.data.source.user

import kotlinx.coroutines.flow.Flow

interface CacheUserDataSource {

    suspend fun createUserName(name: String): Flow<Boolean>

    fun readUserName(): Flow<String>
}