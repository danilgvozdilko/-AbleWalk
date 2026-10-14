package com.batumove.geogo.domain.source

import com.batumove.geogo.domain.model.AirQuality

interface AirQualitySource {

    suspend fun getAirQuality(
        latitude: Double,
        longitude: Double,
    ): Result<AirQuality>

}