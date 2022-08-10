package com.gotz.domain.usecase.user

import com.gotz.domain.repository.user.UserRepository
import io.reactivex.Single

internal typealias ReadSingleNameBaseUseCase = () -> Single<String>

class ReadSingleNameUseCase(
    private val userRepository: UserRepository
) : ReadSingleNameBaseUseCase{
    override fun invoke(): Single<String> =
        userRepository.readUserName()
}
