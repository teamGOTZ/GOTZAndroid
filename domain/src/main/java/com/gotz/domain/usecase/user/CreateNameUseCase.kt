package com.gotz.domain.usecase.user

import com.gotz.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

internal typealias CreateNameBaseUseCase = suspend (String) -> Flow<Boolean>

class CreateNameUseCase(
    private val userRepository: UserRepository
):CreateNameBaseUseCase {
    override suspend fun invoke(name: String): Flow<Boolean> =
        userRepository.createUserName(name)
}