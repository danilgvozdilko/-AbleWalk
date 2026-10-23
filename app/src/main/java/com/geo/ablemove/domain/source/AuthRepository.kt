package com.geo.ablemove.domain.source

import com.geo.ablemove.domain.model.AuthUser

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): AuthUser
}