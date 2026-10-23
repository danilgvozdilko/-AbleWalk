package com.geo.ablemove.presentation.splash

sealed interface SplashUiState {
    data object Loading : SplashUiState
    data object Authenticated : SplashUiState
    data object Unauthenticated : SplashUiState
}