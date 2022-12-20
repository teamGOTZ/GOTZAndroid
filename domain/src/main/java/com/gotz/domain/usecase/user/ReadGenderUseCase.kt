package com.gotz.domain.usecase.user

import com.gotz.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

internal typealias ReadGenderBaseUseCase = suspend () -> Flow<String>

class ReadGenderUseCase(
    private val userRepository: UserRepository
): ReadGenderBaseUseCase {
    override suspend fun invoke(): Flow<String> {
        return userRepository.readGender()
    }
}