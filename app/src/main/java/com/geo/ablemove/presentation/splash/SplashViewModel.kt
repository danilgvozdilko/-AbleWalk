package com.geo.ablemove.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geo.ablemove.domain.datastore.UserDataPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userDataPreferences: UserDataPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(
        SplashUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        getLoginStatus()
    }

    private fun getLoginStatus() {
        viewModelScope.launch {
            try {
                val isLogined = userDataPreferences.getIsLogined()

                _uiState.value = if (isLogined) {
                    SplashUiState.Authenticated
                } else {
                    SplashUiState.Unauthenticated
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = SplashUiState.Unauthenticated
            }
        }
    }
}