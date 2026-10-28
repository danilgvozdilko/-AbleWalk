package com.geo.ablemove.presentation.registration

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geo.ablemove.domain.datastore.UserDataPreferences
import com.geo.ablemove.domain.model.AuthUser
import com.geo.ablemove.domain.usecase.RegisterWithEmailUseCase
import com.geo.ablemove.domain.usecase.SignInWithFacebookUseCase
import com.geo.ablemove.domain.usecase.SignInWithGoogleUseCase
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerWithEmailUseCase: RegisterWithEmailUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signInWithFacebookUseCase: SignInWithFacebookUseCase,
    private val userDataPreferences: UserDataPreferences
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<RegistrationUiState>(
            RegistrationUiState.Idle
        )

    val uiState: StateFlow<RegistrationUiState> =
        _uiState.asStateFlow()

    fun registerWithEmail(
        nickname: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (isLoading()) return

        val trimmedNickname = nickname.trim()
        val trimmedEmail = email.trim()

        val validationError = validateRegistration(
            nickname = trimmedNickname,
            email = trimmedEmail,
            password = password,
            confirmPassword = confirmPassword
        )

        if (validationError != null) {
            showError(validationError)
            return
        }

        authenticate {
            registerWithEmailUseCase(
                nickname = trimmedNickname,
                email = trimmedEmail,
                password = password
            )
        }
    }

    fun signInWithGoogle(idToken: String) {
        authenticate {
            signInWithGoogleUseCase(idToken)
        }
    }

    fun signInWithFacebook(accessToken: String) {
        authenticate {
            signInWithFacebookUseCase(accessToken)
        }
    }

    fun onSocialSignInStarted() {
        if (!isLoading()) {
            _uiState.value = RegistrationUiState.Loading
        }
    }

    fun onSocialSignInCancelled() {
        if (isLoading()) {
            _uiState.value = RegistrationUiState.Idle
        }
    }

    fun onSocialSignInError(exception: Exception) {
        handleError(exception)
    }

    fun signInAsGuest() {
        if (isLoading()) return

        viewModelScope.launch {
            _uiState.value = RegistrationUiState.Loading

            try {
                userDataPreferences.setIsLogined(true)
                _uiState.value = RegistrationUiState.Success
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun clearError() {
        if (_uiState.value is RegistrationUiState.Error) {
            _uiState.value = RegistrationUiState.Idle
        }
    }

    private var isFirebaseAuthenticationRunning = false

    private fun authenticate(
        action: suspend () -> AuthUser
    ) {
        if (isFirebaseAuthenticationRunning) return

        isFirebaseAuthenticationRunning = true

        viewModelScope.launch {
            _uiState.value = RegistrationUiState.Loading

            try {
                val user = action()
                completeAuthentication(user)

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                handleError(e)

            } finally {
                isFirebaseAuthenticationRunning = false
            }
        }
    }

    private suspend fun completeAuthentication(user: AuthUser) {
        userDataPreferences.setUserData(user)
        userDataPreferences.setIsLogined(true)

        _uiState.value = RegistrationUiState.Success
    }

    private fun validateRegistration(
        nickname: String,
        email: String,
        password: String,
        confirmPassword: String
    ): String? {
        return when {
            nickname.isBlank() ->
                "Please enter your nickname"

            nickname.length < 3 ->
                "Nickname must contain at least 3 characters"

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Please enter a valid email address"

            password.length < 6 ->
                "Password must contain at least 6 characters"

            password != confirmPassword ->
                "Passwords do not match"

            else -> null
        }
    }

    private fun handleError(exception: Exception) {
        val message = when (exception) {
            is FirebaseAuthUserCollisionException ->
                "An account with this email already exists"

            is FirebaseTooManyRequestsException ->
                "Too many requests. Please try again later"

            else ->
                exception.localizedMessage ?: "Authentication failed"
        }

        showError(message)
    }

    private fun showError(message: String) {
        _uiState.value = RegistrationUiState.Error(message)
    }

    private fun isLoading(): Boolean {
        return _uiState.value is RegistrationUiState.Loading
    }
}