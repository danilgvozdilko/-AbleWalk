package com.geo.ablemove.presentation.registration

data class RegistrationUiState(
    val isLoading: Boolean = false,
    val nicknameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val generalError: String? = null
)

sealed interface RegistrationEffect {
    data object NavigateToHome : RegistrationEffect
}
