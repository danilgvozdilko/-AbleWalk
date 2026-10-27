package com.geo.ablemove.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geo.ablemove.domain.datastore.UserDataPreferences
import com.geo.ablemove.domain.usecase.SignInWithFacebookUseCase
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
    private val signInWithFacebookUseCase: SignInWithFacebookUseCase,
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
                userDataPreferences.setIsLogined(true)
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

    fun onFacebookSignInStarted() {
        if (_uiState.value is LoginUiState.Loading) return
        _uiState.value = LoginUiState.Loading
    }

    fun onFacebookSignInCancelled() {
        _uiState.value = LoginUiState.Idle
    }

    fun onFacebookSignInError(message: String) {
        _uiState.value = LoginUiState.Error(message)
    }

    fun signInWithFacebook(accessToken: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            try {
                Log.d("FacebookAuth", "Starting Firebase authentication")

                val user = signInWithFacebookUseCase(accessToken)

                Log.d("FacebookAuth", "Firebase authentication success: ${user.id}")

                userDataPreferences.setUserData(user)
                userDataPreferences.setIsLogined(true)

                Log.d("FacebookAuth", "User data saved")

                _uiState.value = LoginUiState.Success(user)

                Log.d("FacebookAuth", "LoginUiState.Success emitted")

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("FacebookAuth", "Firebase authentication failed", e)

                _uiState.value = LoginUiState.Error(
                    e.localizedMessage ?: "Facebook sign-in failed"
                )
            }
        }
    }
}