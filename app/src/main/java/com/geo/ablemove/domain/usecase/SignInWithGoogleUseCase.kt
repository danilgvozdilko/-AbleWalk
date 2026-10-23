package com.geo.ablemove.domain.usecase

import com.geo.ablemove.domain.model.AuthUser
import com.geo.ablemove.domain.source.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): AuthUser {
        return repository.signInWithGoogle(idToken)
    }
}