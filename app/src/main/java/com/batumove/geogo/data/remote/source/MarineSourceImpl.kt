package com.batumove.geogo.data.remote.source

import com.batumove.geogo.data.mapper.toDomain
import com.batumove.geogo.data.remote.api.MarineApi
import com.batumove.geogo.data.util.safeApiCall
import com.batumove.geogo.domain.model.Marine
import com.batumove.geogo.domain.source.MarineSource
import javax.inject.Inject

class MarineSourceImpl @Inject constructor(private val marineApi: MarineApi) : MarineSource {

    override suspend fun getMarine(
        latitude: Double,
        longitude: Double,
    ): Result<Marine> {
        return safeApiCall {
            marineApi.getMarine(latitude, longitude).toDomain()
        }
    }

}