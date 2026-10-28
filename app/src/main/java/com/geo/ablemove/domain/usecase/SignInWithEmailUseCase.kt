package com.geo.ablemove.domain.usecase

import com.geo.ablemove.domain.model.AuthUser
import com.geo.ablemove.domain.source.AuthRepository
import jakarta.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): AuthUser {
        return authRepository.signInWithEmail(
            email = email.trim(),
            password = password
        )
    }
}