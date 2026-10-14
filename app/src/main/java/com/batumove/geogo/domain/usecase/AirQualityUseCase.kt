package com.batumove.geogo.domain.usecase

import com.batumove.geogo.data.mapper.toDomain
import com.batumove.geogo.domain.model.AirQuality
import com.batumove.geogo.domain.source.AirQualitySource
import javax.inject.Inject

class AirQualityUseCase @Inject constructor(private val airQualitySource: AirQualitySource) {

    suspend fun getAirQuality(latitude: Double, longitude: Double): AirQuality =
        airQualitySource.getAirQuality(latitude, longitude).toDomain()

}
