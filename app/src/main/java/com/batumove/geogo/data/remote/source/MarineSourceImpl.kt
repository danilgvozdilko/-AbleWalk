package com.batumove.geogo.data.remote.source

import com.batumove.geogo.data.dto.MarineDto
import com.batumove.geogo.data.remote.api.MarineApi
import javax.inject.Inject

class MarineSourceImpl @Inject constructor(private val marineApi: MarineApi) : MarineSource {

    override suspend fun getMarine(
        latitude: Double,
        longitude: Double,
    ): MarineDto {
        return marineApi.getMarine(latitude, longitude)
    }

}