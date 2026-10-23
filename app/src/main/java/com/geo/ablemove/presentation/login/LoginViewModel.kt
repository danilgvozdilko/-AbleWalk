package com.geo.ablemove.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geo.ablemove.domain.datastore.UserDataPreferences
import com.geo.ablemove.domain.usecase.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val userDataPreferences: UserDataPreferences
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<LoginUiState>(LoginUiState.Idle)

    val uiState = _uiState.asStateFlow()

    fun onGoogleSignInStarted() {
        if (_uiState.value is LoginUiState.Loading) return
        _uiState.value = LoginUiState.Loading
    }

    fun onGoogleSignInCancelled() {
        _uiState.value = LoginUiState.Idle
    }

    fun onGoogleSignInError(message: String) {
        _uiState.value = LoginUiState.Error(message)
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            try {
                val user = signInWithGoogleUseCase(idToken)
                userDataPreferences.setUserData(user)
                _uiState.value = LoginUiState.Success(user)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(
                    e.localizedMessage ?: "Google sign-in failed"
                )
            }
        }
    }
}