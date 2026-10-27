package com.geo.ablemove.domain.usecase

import com.geo.ablemove.domain.model.AuthUser
import com.geo.ablemove.domain.source.AuthRepository
import jakarta.inject.Inject

class SignInWithFacebookUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(accessToken: String): AuthUser {
        return repository.signInWithFacebook(accessToken)
    }
}