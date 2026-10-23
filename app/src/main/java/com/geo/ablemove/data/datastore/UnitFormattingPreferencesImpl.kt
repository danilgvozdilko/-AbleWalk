package com.geo.ablemove.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.geo.ablemove.domain.datastore.UnitFormattingPreferences
import com.geo.ablemove.domain.model.DistanceUnit
import com.geo.ablemove.domain.model.TemperatureUnit
import com.geo.ablemove.domain.model.UnitFormatting
import com.geo.ablemove.domain.model.WindSpeedUnit
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UnitFormattingPreferencesImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UnitFormattingPreferences {


    private companion object {
        val TEMPERATURE_KEY = stringPreferencesKey("temperature_unit")
        val WIND_SPEED_KEY = stringPreferencesKey("wind_speed_unit")
        val DISTANCE_KEY = stringPreferencesKey("distance_unit")
    }

    override val unitFormatting: Flow<UnitFormatting> =
        dataStore.data.map { preferences ->
            UnitFormatting(
                temperature = TemperatureUnit.entries.firstOrNull {
                    it.name == preferences[TEMPERATURE_KEY]
                } ?: TemperatureUnit.CELSIUS,

                windSpeed = WindSpeedUnit.entries.firstOrNull {
                    it.name == preferences[WIND_SPEED_KEY]
                } ?: WindSpeedUnit.KMH,

                distance = DistanceUnit.entries.firstOrNull {
                    it.name == preferences[DISTANCE_KEY]
                } ?: DistanceUnit.METRIC
            )
        }

    override suspend fun setTemperatureUnit(unit: TemperatureUnit) {
        dataStore.edit { preferences ->
            preferences[TEMPERATURE_KEY] = unit.name
        }
    }

    override suspend fun setWindSpeedUnit(unit: WindSpeedUnit) {
        dataStore.edit { preferences ->
            preferences[WIND_SPEED_KEY] = unit.name
        }
    }

    override suspend fun setDistanceUnit(unit: DistanceUnit) {
        dataStore.edit { preferences ->
            preferences[DISTANCE_KEY] = unit.name
        }
    }
}