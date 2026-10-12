package com.batumove.geogo.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batumove.geogo.domain.usecase.WeatherUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherUserCase: WeatherUserCase) :
    ViewModel() {
    private val _uiState =
        MutableStateFlow<HomeUiState>(
            HomeUiState.Loading
        )

    val uiState: StateFlow<HomeUiState> =
        _uiState.asStateFlow()

    init {
        loadWeather()
    }

    fun loadWeather() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val weather = weatherUserCase.getWeather()
                _uiState.value = HomeUiState.Content(weather)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error
            }
        }
    }

}