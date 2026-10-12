package com.batumove.geogo.presentation.home

import com.batumove.geogo.domain.model.Weather

sealed interface HomeUiState {

    data object Loading :
        HomeUiState

    data class Content(
        val weather: Weather,
    ) : HomeUiState

    data object Error :
        HomeUiState
}