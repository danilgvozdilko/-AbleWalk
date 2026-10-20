package com.batumove.geogo.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batumove.geogo.domain.datastore.NotificationPreferences
import com.batumove.geogo.domain.datastore.UnitFormattingPreferences
import com.batumove.geogo.domain.model.DistanceUnit
import com.batumove.geogo.domain.model.TemperatureUnit
import com.batumove.geogo.domain.model.UnitFormatting
import com.batumove.geogo.domain.model.WindSpeedUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val unitFormattingPreferences: UnitFormattingPreferences,
    private val notificationPreferences: NotificationPreferences
) : ViewModel() {

    val unitFormatting: StateFlow<UnitFormatting> =
        unitFormattingPreferences.unitFormatting.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UnitFormatting()
        )

    val notificationsEnabled: StateFlow<Boolean> =
        notificationPreferences.notificationsEnabled.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun updateTemperature(unit: TemperatureUnit) {
        viewModelScope.launch {
            unitFormattingPreferences.setTemperatureUnit(unit)
        }
    }

    fun updateWindSpeed(unit: WindSpeedUnit) {
        viewModelScope.launch {
            unitFormattingPreferences.setWindSpeedUnit(unit)
        }
    }

    fun updateDistance(unit: DistanceUnit) {
        viewModelScope.launch {
            unitFormattingPreferences.setDistanceUnit(unit)
        }
    }

    fun updateNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            notificationPreferences.setNotificationsEnabled(enabled)
        }
    }
}