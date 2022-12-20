package com.gotz.domain.usecase.user

import com.gotz.domain.repository.user.UserRepository

internal typealias UpdateGenderBaseUseCase = suspend (String) -> Unit

class UpdateGenderUseCase(
    private val userRepository: UserRepository
): UpdateGenderBaseUseCase {
    override suspend fun invoke(gender: String) {
        userRepository.updateGender(gender)
    }
}