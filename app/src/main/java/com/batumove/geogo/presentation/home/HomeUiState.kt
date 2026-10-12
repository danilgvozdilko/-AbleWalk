package com.batumove.geogo.presentation.home

import com.batumove.geogo.domain.model.Weather

sealed interface HomeUiState {

    data object Loading :
        HomeUiState

    data class Content(
        val weather: Weather,
    ) : HomeUiState

    data class Error(val error: String) :
        HomeUiState
}