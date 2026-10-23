package com.geo.ablemove.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geo.ablemove.domain.datastore.NotificationPreferences
import com.geo.ablemove.domain.datastore.UnitFormattingPreferences
import com.geo.ablemove.domain.datastore.UserDataPreferences
import com.geo.ablemove.domain.model.AuthUser
import com.geo.ablemove.domain.model.DistanceUnit
import com.geo.ablemove.domain.model.TemperatureUnit
import com.geo.ablemove.domain.model.UnitFormatting
import com.geo.ablemove.domain.model.WindSpeedUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val unitFormattingPreferences: UnitFormattingPreferences,
    private val notificationPreferences: NotificationPreferences,
    private val userDataPreferences: UserDataPreferences
) : ViewModel() {

    private val _userState = MutableStateFlow<AuthUser?>(null)
    val userState = _userState.asStateFlow()

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

    init {
        loadUser()
    }

    fun updateTemperature(unit: TemperatureUnit) {
        viewModelScope.launch {
            unitFormattingPreferences.setTemperatureUnit(unit)
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            _userState.value = userDataPreferences.getUserData()
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