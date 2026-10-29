package com.geo.ablemove.presentation.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geo.ablemove.data.mapper.AuthErrorMapper
import com.geo.ablemove.domain.datastore.UserDataPreferences
import com.geo.ablemove.domain.model.AuthUser
import com.geo.ablemove.domain.usecase.SendPasswordResetEmailUseCase
import com.geo.ablemove.domain.usecase.SignInWithEmailUseCase
import com.geo.ablemove.domain.usecase.SignInWithFacebookUseCase
import com.geo.ablemove.domain.usecase.SignInWithGoogleUseCase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signInWithFacebookUseCase: SignInWithFacebookUseCase,
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val resetEmailUseCase: SendPasswordResetEmailUseCase,
    private val userDataPreferences: UserDataPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged() = _uiState.update {
        it.copy(emailError = null, loginError = null)
    }

    fun onPasswordChanged() = _uiState.update {
        it.copy(passwordError = null, loginError = null)
    }

    fun tryStartSocialSignIn(): Boolean {
        if (_uiState.value.isLoading || _uiState.value.isAuthenticated ||
            _uiState.value.passwordReset is PasswordResetState.Loading
        ) return false
        _uiState.update { it.copy(isLoading = true, loginError = null) }
        return true
    }

    fun onSocialSignInCancelled() {
        _uiState.update { it.copy(isLoading = false) }
    }

    fun onSocialSignInError(provider: String, cause: Exception) {
        _uiState.update {
            it.copy(isLoading = false, loginError = AuthErrorMapper.message(cause, provider))
        }
    }

    fun signInWithGoogle(idToken: String) = completeSocialSignIn {
        signInWithGoogleUseCase(idToken)
    }

    fun signInWithFacebook(accessToken: String) = completeSocialSignIn {
        signInWithFacebookUseCase(accessToken)
    }

    private fun completeSocialSignIn(signIn: suspend () -> AuthUser) {
        // Called after tryStartSocialSignIn has reserved the operation.
        viewModelScope.launch {
            try {
                persistUser(signIn())
                _uiState.update { it.copy(isAuthenticated = true) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(loginError = AuthErrorMapper.message(e)) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        if (!canStartLogin()) return
        val normalizedEmail = email.trim()
        val emailError = when {
            normalizedEmail.isBlank() -> "Enter your email"
            !Patterns.EMAIL_ADDRESS.matcher(normalizedEmail)
                .matches() -> "Enter a valid email address"

            else -> null
        }
        val passwordError = if (password.isBlank()) "Enter your password" else null
        if (emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(emailError = emailError, passwordError = passwordError, loginError = null)
            }
            return
        }

        _uiState.update {
            it.copy(isLoading = true, emailError = null, passwordError = null, loginError = null)
        }
        viewModelScope.launch {
            try {
                persistUser(signInWithEmailUseCase(normalizedEmail, password))
                _uiState.update { it.copy(isAuthenticated = true) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(loginError = AuthErrorMapper.message(e)) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun signInGuest() {
        if (!canStartLogin()) return
        _uiState.update { it.copy(isLoading = true, loginError = null) }
        viewModelScope.launch {
            try {
                userDataPreferences.setIsLogined(true)
                _uiState.update { it.copy(isAuthenticated = true) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(loginError = AuthErrorMapper.message(e)) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun canStartLogin(): Boolean =
        !_uiState.value.isLoading && !_uiState.value.isAuthenticated &&
                _uiState.value.passwordReset !is PasswordResetState.Loading

    private suspend fun persistUser(user: AuthUser) {
        userDataPreferences.setUserData(user)
        userDataPreferences.setIsLogined(true)
    }

    fun openPasswordReset() {
        if (_uiState.value.isLoading) return
        _uiState.update { it.copy(passwordReset = PasswordResetState.Idle) }
    }

    fun dismissPasswordReset() {
        if (_uiState.value.passwordReset is PasswordResetState.Loading) return
        _uiState.update { it.copy(passwordReset = PasswordResetState.Idle) }
    }

    fun sendPasswordResetEmail(email: String) {
        if (_uiState.value.isLoading ||
            _uiState.value.passwordReset is PasswordResetState.Loading
        ) return
        val normalizedEmail = email.trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(normalizedEmail).matches()) {
            _uiState.update {
                it.copy(passwordReset = PasswordResetState.Error("Enter a valid email address"))
            }
            return
        }
        _uiState.update { it.copy(passwordReset = PasswordResetState.Loading) }
        viewModelScope.launch {
            try {
                resetEmailUseCase(normalizedEmail)
                _uiState.update { it.copy(passwordReset = PasswordResetState.Sent) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(passwordReset = PasswordResetState.Error(AuthErrorMapper.resetMessage(e)))
                }
            }
        }
    }
}
