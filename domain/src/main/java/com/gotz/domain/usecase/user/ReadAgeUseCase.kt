package com.gotz.domain.usecase.user

import com.gotz.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

internal typealias ReadAgeBaseUseCase = suspend () -> Flow<Int>

class ReadAgeUseCase(
    private val userRepository: UserRepository
): ReadAgeBaseUseCase {
    override suspend fun invoke(): Flow<Int> {
        return userRepository.readAge()
    }
}