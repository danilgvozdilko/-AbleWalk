package com.batumove.geogo.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


}