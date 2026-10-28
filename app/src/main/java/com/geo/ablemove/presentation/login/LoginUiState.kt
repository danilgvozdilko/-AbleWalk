package com.geo.ablemove.presentation.login

import com.geo.ablemove.domain.model.AuthUser

sealed interface LoginUiState {

    data object Idle : LoginUiState

    data object Loading : LoginUiState

    data class Success(
        val user: AuthUser
    ) : LoginUiState

    data class Error(
        val message: String
    ) : LoginUiState

    data object PasswordResetEmailSent : LoginUiState

}