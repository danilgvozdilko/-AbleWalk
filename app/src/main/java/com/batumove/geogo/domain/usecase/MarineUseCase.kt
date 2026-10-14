package com.batumove.geogo.domain.usecase

import com.batumove.geogo.data.mapper.toDomain
import com.batumove.geogo.domain.source.MarineSource
import javax.inject.Inject

class MarineUseCase @Inject constructor(private val marineSource: MarineSource) {

    suspend fun getMarine(latitude: Double, longitude: Double) =
        marineSource.getMarine(latitude, longitude).toDomain()
}