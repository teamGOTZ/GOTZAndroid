package com.gotz.data.repository.user.local

import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {

    suspend fun createUserName(name: String): Flow<Boolean>

    fun readUserName(): Flow<String>

    suspend fun updateUserAge(age: Int)

    fun readUserAge(): Flow<Int>

    suspend fun updateUserGender(gender: String)

    fun readUserGender(): Flow<String>
}