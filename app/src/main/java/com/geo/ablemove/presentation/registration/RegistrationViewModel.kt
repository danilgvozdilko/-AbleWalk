package com.geo.ablemove.presentation.registration

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geo.ablemove.domain.datastore.UserDataPreferences
import com.geo.ablemove.domain.model.AuthUser
import com.geo.ablemove.domain.usecase.RegisterWithEmailUseCase
import com.geo.ablemove.domain.usecase.SignInWithFacebookUseCase
import com.geo.ablemove.domain.usecase.SignInWithGoogleUseCase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerWithEmailUseCase: RegisterWithEmailUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signInWithFacebookUseCase: SignInWithFacebookUseCase,
    private val userDataPreferences: UserDataPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<RegistrationEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()
    private var operationInProgress = false

    fun clearNicknameError() = _uiState.update { it.copy(nicknameError = null, generalError = null) }
    fun clearEmailError() = _uiState.update { it.copy(emailError = null, generalError = null) }
    fun clearPasswordError() = _uiState.update { it.copy(passwordError = null, confirmPasswordError = null, generalError = null) }
    fun clearConfirmPasswordError() = _uiState.update { it.copy(confirmPasswordError = null, generalError = null) }
    fun clearError() = _uiState.update { it.copy(generalError = null) }

    fun registerWithEmail(nickname: String, email: String, password: String, confirmPassword: String) {
        if (operationInProgress) return
        val name = nickname.trim()
        val address = email.trim()
        val errors = RegistrationUiState(
            nicknameError = when {
                name.isBlank() -> "Enter a nickname"
                name.length < 3 -> "Nickname must be at least 3 characters"
                else -> null
            },
            emailError = if (!Patterns.EMAIL_ADDRESS.matcher(address).matches()) "Enter a valid email" else null,
            passwordError = when {
                password.isBlank() -> "Enter a password"
                password.length < 6 -> "Use at least 6 characters"
                else -> null
            },
            confirmPasswordError = if (password != confirmPassword) "Passwords do not match" else null
        )
        if (listOf(errors.nicknameError, errors.emailError, errors.passwordError, errors.confirmPasswordError).any { it != null }) {
            _uiState.value = errors
            return
        }
        authenticate { registerWithEmailUseCase(nickname = name, email = address, password = password) }
    }

    fun onSocialSignInStarted(): Boolean {
        if (operationInProgress) return false
        operationInProgress = true
        _uiState.update { it.copy(isLoading = true, generalError = null) }
        return true
    }

    fun onSocialSignInCancelled() {
        if (!operationInProgress) return
        operationInProgress = false
        _uiState.update { it.copy(isLoading = false) }
    }

    fun onSocialSignInError(exception: Exception) {
        if (!operationInProgress) return
        operationInProgress = false
        _uiState.update { it.copy(isLoading = false, generalError = exception.toFriendlyMessage()) }
    }

    fun signInWithGoogle(idToken: String) = authenticate(alreadyStarted = true) {
        signInWithGoogleUseCase(idToken)
    }

    fun signInWithFacebook(accessToken: String) = authenticate(alreadyStarted = true) {
        signInWithFacebookUseCase(accessToken)
    }

    fun signInAsGuest() {
        if (operationInProgress) return
        operationInProgress = true
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, generalError = null) }
            try {
                userDataPreferences.setIsLogined(true)
                _effects.send(RegistrationEffect.NavigateToHome)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(generalError = e.toFriendlyMessage()) }
            } finally {
                operationInProgress = false
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun authenticate(alreadyStarted: Boolean = false, action: suspend () -> AuthUser) {
        if (!alreadyStarted) {
            if (operationInProgress) return
            operationInProgress = true
        } else if (!operationInProgress) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, generalError = null) }
            try {
                val user = action()
                userDataPreferences.setUserData(user)
                userDataPreferences.setIsLogined(true)
                _effects.send(RegistrationEffect.NavigateToHome)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(generalError = e.toFriendlyMessage()) }
            } finally {
                operationInProgress = false
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun Exception.toFriendlyMessage(): String = when (this) {
        is FirebaseAuthUserCollisionException -> "An account with this email already exists"
        is FirebaseAuthWeakPasswordException -> "Choose a stronger password"
        is FirebaseTooManyRequestsException -> "Too many attempts. Please try again later"
        is FirebaseNetworkException -> "Check your internet connection and try again"
        else -> "Something went wrong. Please try again"
    }
}
