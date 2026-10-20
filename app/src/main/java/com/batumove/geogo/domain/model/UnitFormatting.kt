package com.batumove.geogo.domain.model

data class UnitFormatting(
    val temperature: TemperatureUnit = TemperatureUnit.CELSIUS,
    val windSpeed: WindSpeedUnit = WindSpeedUnit.KMH,
    val distance: DistanceUnit = DistanceUnit.METRIC
)