package com.gotz.domain.usecase.user

import com.gotz.domain.repository.user.UserRepository
import io.reactivex.Single

internal typealias InsertNameBaseUseCase = (String) -> Single<Boolean>

class InsertNameUseCase(
    private val userRepository: UserRepository
):InsertNameBaseUseCase {
    override fun invoke(name: String): Single<Boolean> =
        userRepository.insertUserName(name)
}