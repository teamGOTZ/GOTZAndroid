package com.gotz.domain.usecase.user

import com.gotz.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

internal typealias ReadNameBaseUseCase = suspend () -> Flow<String>

class ReadNameUseCase(
    private val userRepository: UserRepository
) : ReadNameBaseUseCase{
    override suspend fun invoke(): Flow<String> =
        userRepository.readUserName()
}
