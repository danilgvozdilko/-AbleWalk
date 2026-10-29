package com.geo.ablemove.presentation.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val loginError: String? = null,
    val passwordReset: PasswordResetState = PasswordResetState.Idle
)

sealed interface PasswordResetState {
    data object Idle : PasswordResetState
    data object Loading : PasswordResetState
    data object Sent : PasswordResetState
    data class Error(val message: String) : PasswordResetState
}
