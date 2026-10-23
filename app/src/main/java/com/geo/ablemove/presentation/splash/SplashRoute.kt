package com.geo.ablemove.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SplashRoute(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        when (uiState) {
            SplashUiState.Authenticated -> {
                onNavigateToHome()
            }

            SplashUiState.Unauthenticated -> {
                onNavigateToLogin()
            }

            SplashUiState.Loading -> Unit
        }
    }

    SplashScreen()
}

@Composable
fun SplashScreen() {

}