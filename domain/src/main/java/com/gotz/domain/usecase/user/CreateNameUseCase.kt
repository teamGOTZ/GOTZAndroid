package com.gotz.domain.usecase.user

import com.gotz.domain.repository.user.UserRepository
import io.reactivex.Single

internal typealias CreateNameBaseUseCase = (String) -> Single<Boolean>

class CreateNameUseCase(
    private val userRepository: UserRepository
):CreateNameBaseUseCase {
    override fun invoke(name: String): Single<Boolean> =
        userRepository.createUserName(name)
}