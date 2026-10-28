package com.geo.ablemove.domain.usecase

import com.geo.ablemove.domain.source.AuthRepository
import javax.inject.Inject

class SendPasswordResetEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String) {
        authRepository.sendPasswordResetEmail(
            email.trim()
        )
    }
}