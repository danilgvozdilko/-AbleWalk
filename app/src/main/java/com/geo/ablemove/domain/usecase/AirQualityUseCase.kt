package com.geo.ablemove.domain.usecase

import com.geo.ablemove.domain.model.AirQuality
import com.geo.ablemove.domain.source.AirQualitySource
import javax.inject.Inject

class AirQualityUseCase @Inject constructor(private val airQualitySource: AirQualitySource) {
    suspend fun getAirQuality(latitude: Double, longitude: Double): Result<AirQuality> =
        airQualitySource.getAirQuality(latitude, longitude)

}
