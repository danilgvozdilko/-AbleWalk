package com.batumove.geogo.data.remote.source

import com.batumove.geogo.data.mapper.toDomain
import com.batumove.geogo.data.remote.api.AirQualityApi
import com.batumove.geogo.data.util.safeApiCall
import com.batumove.geogo.domain.model.AirQuality
import com.batumove.geogo.domain.source.AirQualitySource
import javax.inject.Inject

class AirQualitySourceImpl @Inject constructor(private val airQualityApi: AirQualityApi) :
    AirQualitySource {

    override suspend fun getAirQuality(
        latitude: Double,
        longitude: Double,
    ): Result<AirQuality> {
        return safeApiCall {
            airQualityApi.getAirQuality(latitude, longitude).toDomain()
        }

    }

}