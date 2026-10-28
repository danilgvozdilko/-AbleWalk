package com.geo.ablemove.domain.source

import com.geo.ablemove.domain.model.AuthUser

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): AuthUser
    suspend fun signInWithFacebook(accessToken: String): AuthUser

    suspend fun signInWithEmail(
        email: String,
        password: String
    ): AuthUser

    suspend fun sendPasswordResetEmail(email: String)

    suspend fun registerWithEmail(
        nickname: String,
        email: String,
        password: String
    ): AuthUser

}