package com.geo.ablemove.data.mapper

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import java.io.IOException

object AuthErrorMapper {
    fun message(error: Throwable, provider: String? = null): String = when {
        error is FirebaseNetworkException || error is IOException ->
            "Check your internet connection and try again"

        error is FirebaseAuthException && error.errorCode == "ERROR_TOO_MANY_REQUESTS" ->
            "Too many attempts. Please try again later"

        error is FirebaseAuthException && error.errorCode in setOf(
            "ERROR_INVALID_CREDENTIAL", "ERROR_WRONG_PASSWORD", "ERROR_USER_NOT_FOUND",
            "ERROR_INVALID_EMAIL", "ERROR_USER_DISABLED"
        ) -> "Unable to sign in. Check your credentials or use password recovery"

        else -> if (provider == null) "Something went wrong. Please try again" else
            "$provider sign-in failed. Please try again"
    }

    fun resetMessage(error: Throwable): String = when {
        error is FirebaseNetworkException || error is IOException ->
            "Check your internet connection and try again"

        error is FirebaseAuthException && error.errorCode == "ERROR_TOO_MANY_REQUESTS" ->
            "Too many requests. Please try again later"

        else -> "Could not send the reset email. Please try again"
    }
}