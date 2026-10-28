package com.geo.ablemove.domain.usecase

import com.geo.ablemove.domain.model.AuthUser
import com.geo.ablemove.domain.source.AuthRepository
import jakarta.inject.Inject

class RegisterWithEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        nickname: String,
        email: String,
        password: String
    ): AuthUser {
        return authRepository.registerWithEmail(
            nickname = nickname,
            email = email,
            password = password
        )
    }
}