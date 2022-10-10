package com.gotz.domain.usecase.user

import com.gotz.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

internal typealias ReadSingleNameBaseUseCase = suspend () -> Flow<String>

class ReadSingleNameUseCase(
    private val userRepository: UserRepository
) : ReadSingleNameBaseUseCase{
    override suspend fun invoke(): Flow<String> =
        userRepository.readUserName()
}
