package com.geo.ablemove.domain.datastore

import com.geo.ablemove.domain.model.DistanceUnit
import com.geo.ablemove.domain.model.TemperatureUnit
import com.geo.ablemove.domain.model.UnitFormatting
import com.geo.ablemove.domain.model.WindSpeedUnit
import kotlinx.coroutines.flow.Flow

interface UnitFormattingPreferences {
    val unitFormatting: Flow<UnitFormatting>
    suspend fun setTemperatureUnit(unit: TemperatureUnit)
    suspend fun setWindSpeedUnit(unit: WindSpeedUnit)
    suspend fun setDistanceUnit(unit: DistanceUnit)
}
