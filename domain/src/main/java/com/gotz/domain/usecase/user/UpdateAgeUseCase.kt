package com.gotz.domain.usecase.user

import com.gotz.domain.repository.user.UserRepository

internal typealias UpdateAgeBaseUseCase = suspend (Int) -> Unit

class UpdateAgeUseCase(
    private val userRepository: UserRepository
): UpdateAgeBaseUseCase {
    override suspend fun invoke(age: Int) {
        userRepository.updateAge(age)
    }
}