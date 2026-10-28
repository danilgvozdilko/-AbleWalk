package com.geo.ablemove.data.datasource

import com.geo.ablemove.domain.model.AuthUser
import com.geo.ablemove.domain.source.AuthRepository
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.userProfileChangeRequest
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

    override suspend fun signInWithFacebook(
        accessToken: String
    ): AuthUser {
        val credential = FacebookAuthProvider.getCredential(accessToken)

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

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): AuthUser {
        val result = firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .await()

        val firebaseUser = result.user
            ?: throw IllegalStateException(
                "Firebase user is null"
            )

        return AuthUser(
            id = firebaseUser.uid,
            email = firebaseUser.email,
            displayName = firebaseUser.displayName,
            photoUrl = firebaseUser.photoUrl.toString()
        )
    }

    override suspend fun sendPasswordResetEmail(
        email: String
    ) {
        firebaseAuth
            .sendPasswordResetEmail(email)
            .await()
    }

    override suspend fun registerWithEmail(
        nickname: String,
        email: String,
        password: String
    ): AuthUser {
        val result = firebaseAuth
            .createUserWithEmailAndPassword(
                email.trim(),
                password
            )
            .await()

        val firebaseUser = result.user
            ?: error("Firebase user is null")

        val profileUpdates = userProfileChangeRequest {
            displayName = nickname.trim()
        }

        firebaseUser.updateProfile(profileUpdates).await()

        return AuthUser(
            id = firebaseUser.uid,
            email = firebaseUser.email,
            displayName = firebaseUser.displayName,
            photoUrl = firebaseUser.photoUrl.toString()
        )
    }

}