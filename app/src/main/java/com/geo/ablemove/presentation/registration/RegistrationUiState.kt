package com.geo.ablemove.presentation.registration

sealed interface RegistrationUiState {
    data object Idle : RegistrationUiState
    data object Loading : RegistrationUiState
    data object Success : RegistrationUiState
    data class Error(val message: String) : RegistrationUiState
}