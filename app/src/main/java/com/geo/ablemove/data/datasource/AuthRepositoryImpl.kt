package com.geo.ablemove.data.datasource

import com.geo.ablemove.domain.model.AuthUser
import com.geo.ablemove.domain.source.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signInWithGoogle(
        idToken: String
    ): AuthUser {
        val credential = GoogleAuthProvider.getCredential(
            idToken,
            null
        )

        val result = firebaseAuth
            .signInWithCredential(credential)
            .await()

        val user = requireNotNull(result.user) {
            "Firebase user is null"
        }

        return AuthUser(
            id = user.uid,
            email = user.email,
            displayName = user.displayName,
            photoUrl = user.photoUrl?.toString()
        )
    }
}