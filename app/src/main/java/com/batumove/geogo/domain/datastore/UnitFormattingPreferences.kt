package com.batumove.geogo.domain.datastore

import com.batumove.geogo.domain.model.DistanceUnit
import com.batumove.geogo.domain.model.TemperatureUnit
import com.batumove.geogo.domain.model.UnitFormatting
import com.batumove.geogo.domain.model.WindSpeedUnit
import kotlinx.coroutines.flow.Flow

interface UnitFormattingPreferences {

    val unitFormatting: Flow<UnitFormatting>

    suspend fun setTemperatureUnit(unit: TemperatureUnit)

    suspend fun setWindSpeedUnit(unit: WindSpeedUnit)

    suspend fun setDistanceUnit(unit: DistanceUnit)
}